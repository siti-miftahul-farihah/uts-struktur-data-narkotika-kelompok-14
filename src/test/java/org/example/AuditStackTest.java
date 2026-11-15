package org.example;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class AuditStackTest {

    @Test
    void testPushPopPeekLifo() {
        AuditStack<String> stack = new AuditStack<>();
        assertTrue(stack.isEmpty());

        stack.push("IMPOR: 50 putusan dimuat");
        stack.push("CARI: P010 ditemukan dalam 10 langkah");
        stack.push("FILTER: Jenis = Sabu");

        assertEquals(3, stack.size());
        assertEquals("FILTER: Jenis = Sabu", stack.peek());
        stack.validateInvariant();

        assertEquals("FILTER: Jenis = Sabu", stack.pop());
        assertEquals("CARI: P010 ditemukan dalam 10 langkah", stack.pop());
        assertEquals(1, stack.size());
        stack.validateInvariant();
    }

    @Test
    void testSearchPreserveTidakMengubahUrutan() {
        // Skenario 6 Panduan: Pencarian pada Stack tidak boleh mengubah urutan akhir LIFO
        AuditStack<String> stack = new AuditStack<>();
        stack.push("LOG-1: Impor CSV");
        stack.push("LOG-2: Cari P005");
        stack.push("LOG-3: Filter Ganja");
        stack.push("LOG-4: Ekspor Laporan");

        // LOG-4 berada di posisi 1 (puncak), LOG-2 berada di posisi 3
        int posisiDitemukan = stack.searchPreserve("LOG-2: Cari P005");
        assertEquals(3, posisiDitemukan);

        // Pencarian data yang tidak ada mengembalikan -1
        assertEquals(-1, stack.searchPreserve("LOG-999: Tidak Ada"));

        // Buktikan urutan LIFO akhir tetap sama persis setelah pencarian
        assertEquals(4, stack.size());
        assertEquals("LOG-4: Ekspor Laporan", stack.pop());
        assertEquals("LOG-3: Filter Ganja", stack.pop());
        assertEquals("LOG-2: Cari P005", stack.pop());
        assertEquals("LOG-1: Impor CSV", stack.pop());
        assertTrue(stack.isEmpty());
        stack.validateInvariant();
    }

    @Test
    void testEdgeCases() {
        AuditStack<String> stack = new AuditStack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
        assertThrows(NoSuchElementException.class, stack::peek);
        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
        stack.validateInvariant();
    }
}