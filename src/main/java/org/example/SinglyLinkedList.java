package org.example;

import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements StrukturData<T> {

    protected static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    protected Node<T> head;
    protected Node<T> tail;
    protected int size;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        validateInvariant();
    }

    @Override
    public void add(T value) {
        addLast(value);
    }

    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data tidak boleh null.");
        }
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
        validateInvariant();
    }

    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data tidak boleh null.");
        }
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        validateInvariant();
    }

    public void insert(int position, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data tidak boleh null.");
        }
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Indeks insert di luar batas: " + position);
        }
        if (position == 0) {
            addFirst(data);
            return;
        }
        if (position == size) {
            addLast(data);
            return;
        }
        Node<T> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }
        Node<T> newNode = new Node<>(data);
        newNode.next = prev.next;
        prev.next = newNode;
        size++;
        validateInvariant();
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List kosong, tidak ada elemen yang dapat dihapus.");
        }
        T removedData = head.data;
        head = head.next;
        size--;
        if (size == 0) {
            tail = null;
        }
        validateInvariant();
        return removedData;
    }

    @Override
    public T remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Indeks hapus di luar batas: " + position);
        }
        if (position == 0) {
            return removeFirst();
        }
        Node<T> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.next;
        }
        Node<T> target = prev.next;
        prev.next = target.next;
        if (target == tail) {
            tail = prev;
        }
        size--;
        validateInvariant();
        return target.data;
    }

    @Override
    public T get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Indeks di luar batas: " + position);
        }
        Node<T> current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int search(T value) {
        if (value == null) return -1;
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(value)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public boolean contains(T value) {
        return search(value) != -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
        validateInvariant();
    }

    public void validateInvariant() {
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