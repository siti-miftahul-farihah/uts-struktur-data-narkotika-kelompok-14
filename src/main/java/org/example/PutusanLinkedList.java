package org.example;

import java.util.NoSuchElementException;

public class PutusanLinkedList {

    private static class Node {
        PutusanNarkotika data;
        Node next;

        Node(PutusanNarkotika data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public PutusanLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        checkInvariant();
    }

    /**
     * Menambahkan data putusan di akhir list (O(1) dengan pointer tail).
     */
    public void addLast(PutusanNarkotika data) {
        if (data == null) {
            throw new IllegalArgumentException("Data PutusanNarkotika tidak boleh null.");
        }
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        checkInvariant();
    }

    /**
     * Mencari putusan berdasarkan ID putusan (O(n)).
     */
    public PutusanNarkotika findById(String idPutusan) {
        if (idPutusan == null || idPutusan.isBlank()) {
            return null;
        }
        Node current = head;
        while (current != null) {
            if (current.data.getIdPutusan().equalsIgnoreCase(idPutusan)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Menghapus dan mengembalikan elemen pertama (O(1)).
     */
    public PutusanNarkotika removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List kosong, tidak ada elemen yang dapat dihapus.");
        }
        PutusanNarkotika removedData = head.data;
        head = head.next;
        size--;
        if (size == 0) {
            tail = null;
        }
        checkInvariant();
        return removedData;
    }

    /**
     * Mengambil elemen pada indeks tertentu (O(n)).
     */
    public PutusanNarkotika get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indeks di luar batas: " + index);
        }
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Memastikan konsistensi struktur internal (invariant) setiap kali operasi selesai.
     */
    private void checkInvariant() {
        if (size < 0) {
            throw new IllegalStateException("Invariant dilanggar: size negatif.");
        }
        if (size == 0 && (head != null || tail != null)) {
            throw new IllegalStateException("Invariant dilanggar: list kosong tetapi head/tail tidak null.");
        }
        if (size == 1 && (head != tail || head == null)) {
            throw new IllegalStateException("Invariant dilanggar: size 1 tetapi head != tail.");
        }
        if (size > 1 && (head == null || tail == null || tail.next != null)) {
            throw new IllegalStateException("Invariant dilanggar: pointer head/tail tidak valid.");
        }
    }
}
