package org.example;

import java.util.NoSuchElementException;

public class AuditStack<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node<T> top;
    private int size;

    public AuditStack() {
        this.top = null;
        this.size = 0;
        validateInvariant();
    }

    /**
     * Menambahkan elemen ke puncak stack (LIFO - O(1)).
     */
    public void push(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Nilai yang dimasukkan ke stack tidak boleh null.");
        }
        Node<T> newNode = new Node<>(value);
        newNode.next = top;
        top = newNode;
        size++;
        validateInvariant();
    }

    /**
     * Mengambil dan menghapus elemen paling atas dari stack (LIFO - O(1)).
     */
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack kosong, tidak ada elemen yang dapat di-pop.");
        }
        T removed = top.data;
        top = top.next;
        size--;
        validateInvariant();
        return removed;
    }

    /**
     * Melihat elemen paling atas tanpa menghapusnya (O(1)).
     */
    public T peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack kosong.");
        }
        return top.data;
    }

    /**
     * Pencarian non-destruktif: memindahkan elemen ke stack bantu sementara,
     * lalu memanggil restore() agar urutan akhir LIFO tidak berubah.
     * Mengembalikan posisi berbasis 1 dari puncak (1 = top), atau -1 jika tidak ditemukan.
     */
    public int searchPreserve(T target) {
        if (target == null || isEmpty()) {
            return -1;
        }

        AuditStack<T> tempStack = new AuditStack<>();
        int position = 1;
        int foundPosition = -1;

        // Pop satu per satu ke stack sementara sambil memeriksa kecocokan
        while (!this.isEmpty()) {
            T current = this.pop();
            if (foundPosition == -1 && current.equals(target)) {
                foundPosition = position;
            }
            tempStack.push(current);
            position++;
        }

        // Kembalikan seluruh elemen ke stack utama agar urutan tetap identik
        restore(tempStack);
        validateInvariant();
        return foundPosition;
    }

    /**
     * Memulihkan elemen dari stack sementara kembali ke stack utama.
     */
    public void restore(AuditStack<T> tempStack) {
        if (tempStack == null) {
            throw new IllegalArgumentException("Stack sementara tidak boleh null.");
        }
        while (!tempStack.isEmpty()) {
            this.push(tempStack.pop());
        }
        validateInvariant();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        top = null;
        size = 0;
        validateInvariant();
    }

    /**
     * Memastikan konsistensi struktur internal stack.
     */
    public void validateInvariant() {
        if (size < 0) {
            throw new IllegalStateException("Invariant dilanggar: size stack negatif.");
        }
        if (size == 0 && top != null) {
            throw new IllegalStateException("Invariant dilanggar: stack kosong tetapi top tidak null.");
        }
        if (size > 0 && top == null) {
            throw new IllegalStateException("Invariant dilanggar: size > 0 tetapi top null.");
        }

        int count = 0;
        Node<T> current = top;
        while (current != null) {
            count++;
            current = current.next;
        }
        if (count != size) {
            throw new IllegalStateException("Invariant dilanggar: jumlah node (" + count + ") tidak sama dengan size (" + size + ").");
        }
    }
}