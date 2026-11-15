package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PutusanCsvParserTest {

    @Test
    void testParseLineValid() {
        String baris = "P047,294/Pid.Sus/2026/PN Bks,Pengadilan Negeri Bekasi,2026-08-12,T047,Ganja,21.687,\"Kesatu: Pasal 114 ayat (1)\",\"Pasal 114 ayat (1)\",90,72,0,\"1 bungkus kertas berisi Ganja\",\"Tidak mendukung program pemerintah\",\"Bersikap sopan\",1-23";
        PutusanNarkotika hasil = PutusanCsvParser.parseLine(baris);

        assertNotNull(hasil);
        assertEquals("P047", hasil.getCaseId());
        assertEquals("Ganja", hasil.getJenisNarkotika());
        assertEquals(21.687, hasil.getBeratBarangBuktiGram(), 0.0001);
        assertEquals(72, hasil.getVonisBulan());
    }

    @Test
    void testParseLineInvalidDanEdgeCases() {
        // Baris null dan kosong
        assertNull(PutusanCsvParser.parseLine(null));
        assertNull(PutusanCsvParser.parseLine("   "));

        // Kolom kurang dari 10
        assertNull(PutusanCsvParser.parseLine("PUT-001,101/Pid.Sus/2024,PN Malang"));

        // Angka tidak valid (huruf pada kolom tahun/berat)
        assertNull(PutusanCsvParser.parseLine("PUT-001,101/Pid.Sus/2024/PN Mlg,PN Malang,BUKAN_TAHUN,Sabu,0.45,Pasal 112,48,800000000,Inkrah"));

        // Nilai negatif
        assertNull(PutusanCsvParser.parseLine("PUT-001,101/Pid.Sus/2024/PN Mlg,PN Malang,2024,Sabu,-1.5,Pasal 112,48,800000000,Inkrah"));
    }

    @Test
    void testParseFileDataset() throws IOException {
        PutusanLinkedList list = PutusanCsvParser.parseFile("data/putusan-50.csv");
        assertNotNull(list);
        assertEquals(50, list.size());
        assertEquals("P001", list.get(0).getIdPutusan());
    }
}