package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

class PutusanLinkedListTest {

    private PutusanNarkotika buatContohPutusan(String id, String jenis) {
        return new PutusanNarkotika(
                id,
                "294/Pid.Sus/2026/PN Bks",
                "Pengadilan Negeri Bekasi",
                LocalDate.of(2026, 8, 12),
                "T047",
                jenis,
                21.687,
                "Kesatu: Pasal 114 ayat (1)",
                "Pasal 114 ayat (1)",
                90,
                72,
                0L,
                "1 bungkus kertas berisi " + jenis,
                "Tidak mendukung program pemerintah",
                "Bersikap sopan",
                "1-23"
        );
    }

    @Test
    void testMuat50RecordDanBatasIndeks() throws IOException {
        // Skenario 1 & 2: Muat 50 record, buktikan size = 50, dan uji posisi batas[cite: 17]
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        assertEquals(50, list.size());
        list.validateInvariant();

        // Posisi batas awal (indeks 0) dan akhir (indeks 49)[cite: 17]
        assertEquals("P001", list.get(0).getCaseId());
        assertEquals("P050", list.get(49).getCaseId());
    }

    @Test
    void testAddLastDanGet() {
        PutusanLinkedList list = new PutusanLinkedList();
        assertTrue(list.isEmpty());

        PutusanNarkotika p1 = buatContohPutusan("P001", "Sabu");
        PutusanNarkotika p2 = buatContohPutusan("P002", "Ganja");

        list.addLast(p1);
        list.addLast(p2);

        assertEquals(2, list.size());
        assertFalse(list.isEmpty());
        assertEquals("P001", list.get(0).getCaseId());
        assertEquals("P002", list.get(1).getCaseId());
        list.validateInvariant();
    }

    @Test
    void testFindById() {
        // Skenario 2: Cari perkara yang ada dan tidak ada[cite: 17]
        PutusanLinkedList list = new PutusanLinkedList();
        list.addLast(buatContohPutusan("P001", "Sabu"));
        list.addLast(buatContohPutusan("P002", "Ekstasi"));

        PutusanNarkotika ditemukan = list.findById("P002");
        assertNotNull(ditemukan);
        assertEquals("Ekstasi", ditemukan.getJenisNarkotika());

        assertNull(list.findById("P999"));
        assertNull(list.findById(null));
    }

    @Test
    void testHapusAwalTengahAkhirDanTambahKembali() {
        // Skenario 3 & 4: Hapus pertama, tengah, terakhir, lalu tambah 3 record & cek invariant[cite: 17]
        PutusanLinkedList list = new PutusanLinkedList();
        list.addLast(buatContohPutusan("P001", "Sabu"));
        list.addLast(buatContohPutusan("P002", "Ganja"));
        list.addLast(buatContohPutusan("P003", "Ekstasi"));
        list.addLast(buatContohPutusan("P004", "Sabu"));
        list.addLast(buatContohPutusan("P005", "Ganja"));

        // Hapus tengah (indeks 2 -> P003)[cite: 17]
        assertEquals("P003", list.remove(2).getCaseId());
        list.validateInvariant();

        // Hapus pertama (indeks 0 -> P001)[cite: 17]
        assertEquals("P001", list.remove(0).getCaseId());
        list.validateInvariant();

        // Hapus terakhir (indeks 2 -> P005)[cite: 17]
        assertEquals("P005", list.remove(list.size() - 1).getCaseId());
        list.validateInvariant();
        assertEquals(2, list.size());

        // Tambahkan kembali 3 record dan pastikan urutan & invariant konsisten[cite: 17]
        list.addLast(buatContohPutusan("P006", "Sabu"));
        list.addLast(buatContohPutusan("P007", "Ganja"));
        list.addLast(buatContohPutusan("P008", "Ekstasi"));
        assertEquals(5, list.size());
        assertEquals("P008", list.get(4).getCaseId());
        list.validateInvariant();
    }

    @Test
    void testRemoveFirstDanEdgeCases() {
        PutusanLinkedList list = new PutusanLinkedList();

        // Edge case: hapus dari list kosong
        assertThrows(NoSuchElementException.class, list::removeFirst);

        // Edge case: tambah data null
        assertThrows(IllegalArgumentException.class, () -> list.addLast(null));

        // Tambah 1 elemen lalu hapus hingga kosong kembali
        list.addLast(buatContohPutusan("P001", "Sabu"));
        PutusanNarkotika dihapus = list.removeFirst();

        assertEquals("P001", dihapus.getCaseId());
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        list.validateInvariant();
    }
}