package org.example;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class PutusanLinkedListTest {

    private PutusanNarkotika buatContohPutusan(String id, String jenis) {
        return new PutusanNarkotika(
                id,
                "123/Pid.Sus/2025/PN Mlg",
                "PN Malang",
                2025,
                jenis,
                1.5,
                "Pasal 112 ayat (1) UU Narkotika",
                48,
                800000000L,
                "Berkekuatan Hukum Tetap"
        );
    }

    @Test
    void testAddLastDanGet() {
        PutusanLinkedList list = new PutusanLinkedList();
        assertTrue(list.isEmpty());

        PutusanNarkotika p1 = buatContohPutusan("PUT-001", "Sabu");
        PutusanNarkotika p2 = buatContohPutusan("PUT-002", "Ganja");

        list.addLast(p1);
        list.addLast(p2);

        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
        assertEquals("PUT-001", list.get(0).getIdPutusan());
        assertEquals("PUT-002", list.get(1).getIdPutusan());
    }

    @Test
    void testFindById() {
        PutusanLinkedList list = new PutusanLinkedList();
        list.addLast(buatContohPutusan("PUT-001", "Sabu"));
        list.addLast(buatContohPutusan("PUT-002", "Ekstasi"));

        PutusanNarkotika ditemukan = list.findById("PUT-002");
        assertNotNull(ditemukan);
        assertEquals("Ekstasi", ditemukan.getJenisNarkotika());

        assertNull(list.findById("PUT-999"));
        assertNull(list.findById(null));
    }

    @Test
    void testRemoveFirstDanEdgeCases() {
        PutusanLinkedList list = new PutusanLinkedList();

        // Edge case: hapus dari list kosong
        assertThrows(NoSuchElementException.class, list::removeFirst);

        // Edge case: tambah data null
        assertThrows(IllegalArgumentException.class, () -> list.addLast(null));

        // Tambah 1 elemen lalu hapus hingga kosong kembali
        list.addLast(buatContohPutusan("PUT-001", "Sabu"));
        PutusanNarkotika dihapus = list.removeFirst();

        assertEquals("PUT-001", dihapus.getIdPutusan());
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }
}