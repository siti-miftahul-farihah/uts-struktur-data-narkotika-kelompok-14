package org.example;

public class PutusanLinkedList extends SinglyLinkedList<PutusanNarkotika> {

    public PutusanNarkotika findById(String idPutusan) {
        if (idPutusan == null || idPutusan.isBlank()) {
            return null;
        }
        Node<PutusanNarkotika> current = head;
        while (current != null) {
            if (current.data.getCaseId().equalsIgnoreCase(idPutusan)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }
}