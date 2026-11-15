package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PutusanServiceTest {

    @Test
    void testCariDenganPencacahLangkah() throws IOException {
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        PutusanService service = new PutusanService(list);

        // P001 berada di posisi ke-1 -> 1 langkah
        PutusanService.SearchResult resAwal = service.cariPutusan("P001");
        assertNotNull(resAwal.getPutusan());
        assertEquals(1, resAwal.getSteps());

        // P050 berada di posisi ke-50 -> 50 langkah
        PutusanService.SearchResult resAkhir = service.cariPutusan("P050");
        assertNotNull(resAkhir.getPutusan());
        assertEquals(50, resAkhir.getSteps());

        // Tidak ada -> 50 langkah
        PutusanService.SearchResult resKosong = service.cariPutusan("P999");
        assertNull(resKosong.getPutusan());
        assertEquals(50, resKosong.getSteps());
    }

    @Test
    void testFilterGabunganDanAgregasi() throws IOException {
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        PutusanService service = new PutusanService(list);

        // Skenario 5: Filter gabungan pada dataset 50 putusan
        PutusanLinkedList hasilFilter = service.filterGabungan("Sabu", 1.0, 50.0, "114", 60, 120);
        assertNotNull(hasilFilter);
        hasilFilter.validateInvariant();

        // Uji agregasi seluruh 50 perkara
        PutusanService.AggregationResult agg = service.hitungAgregasi();
        assertEquals(50, agg.getTotalPerkara());
        assertTrue(agg.getRataRataVonisBulan() > 0);
        assertTrue(agg.getJumlahJenisUnik() >= 1);
    }

    @Test
    void testUrutkanVonisTanpaMerusakInvariant() throws IOException {
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        PutusanService service = new PutusanService(list);

        PutusanLinkedList terurut = service.urutkanBerdasarkanVonis(true);
        assertEquals(50, terurut.size());
        terurut.validateInvariant();
        list.validateInvariant();

        // Pastikan urutan menaik (ascending)
        for (int i = 0; i < terurut.size() - 1; i++) {
            assertTrue(terurut.get(i).getVonisBulan() <= terurut.get(i + 1).getVonisBulan());
        }
        // Struktur utama asli tetap mempertahankan P001 di indeks 0
        assertEquals("P001", list.get(0).getCaseId());
    }

    @Test
    void testEksporKeFileDanAudit() throws IOException {
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        PutusanService service = new PutusanService(list);

        File tempFile = File.createTempFile("ekspor-test-", ".csv");
        tempFile.deleteOnExit();

        service.eksporKeFile(list, tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        // Pastikan audit stack mencatat aktivitas ekspor di posisi paling atas
        assertTrue(service.getRiwayatAudit().peek().startsWith("EKSPOR:"));
    }
}