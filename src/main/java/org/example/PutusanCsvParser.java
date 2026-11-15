package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PutusanCsvParser {

    public static PutusanLinkedList parseFile(String filePath) throws IOException {
        PutusanLinkedList list = new PutusanLinkedList();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line;
            boolean isHeader = true;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                if (line.isBlank()) {
                    continue;
                }
                PutusanNarkotika putusan = parseLine(line);
                if (putusan != null) {
                    list.addLast(putusan);
                } else {
                    System.err.println("Peringatan: Baris ke-" + lineNumber + " tidak valid dan dilewati.");
                }
            }
        }
        return list;
    }

    public static PutusanNarkotika parseLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] cols = splitCsvLine(line);
        if (cols.length < 16) {
            return null;
        }
        try {
            String caseId = cleanText(cols[0]);
            String nomorPerkara = cleanText(cols[1]);
            String pengadilan = cleanText(cols[2]);

            String tglStr = cleanText(cols[3]);
            LocalDate tanggalPutusan = (tglStr == null) ? null : LocalDate.parse(tglStr);

            String terdakwaKode = cleanText(cols[4]);
            String jenisNarkotika = cleanText(cols[5]);
            double beratGram = parseDoubleOrZero(cols[6]);
            String pasalDakwaan = cleanText(cols[7]);
            String pasalTerbukti = cleanText(cols[8]);
            int tuntutanBulan = parseIntOrZero(cols[9]);
            int vonisBulan = parseIntOrZero(cols[10]);
            long dendaRupiah = parseLongOrZero(cols[11]);
            String barangBukti = cleanText(cols[12]);
            String keadaanMemberatkan = cleanText(cols[13]);
            String keadaanMeringankan = cleanText(cols[14]);
            String sourcePages = cleanText(cols[15]);

            return new PutusanNarkotika(
                    caseId, nomorPerkara, pengadilan, tanggalPutusan,
                    terdakwaKode, jenisNarkotika, beratGram, pasalDakwaan,
                    pasalTerbukti, tuntutanBulan, vonisBulan, dendaRupiah,
                    barangBukti, keadaanMemberatkan, keadaanMeringankan, sourcePages
            );
        } catch (IllegalArgumentException | DateTimeParseException e) {
            return null;
        }
    }

    private static String cleanText(String raw) {
        if (raw == null) return null;
        String trimmed = raw.trim();
        if (trimmed.startsWith("\"") && trimmed.endsWith("\"") && trimmed.length() >= 2) {
            trimmed = trimmed.substring(1, trimmed.length() - 1).trim();
        }
        if (trimmed.isEmpty() || trimmed.equalsIgnoreCase("null")) {
            return null;
        }
        return trimmed;
    }

    private static double parseDoubleOrZero(String raw) {
        String val = cleanText(raw);
        return (val == null) ? 0.0 : Double.parseDouble(val);
    }

    private static int parseIntOrZero(String raw) {
        String val = cleanText(raw);
        return (val == null) ? 0 : Integer.parseInt(val);
    }

    private static long parseLongOrZero(String raw) {
        String val = cleanText(raw);
        return (val == null) ? 0L : Long.parseLong(val);
    }

    private static String[] splitCsvLine(String line) {
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }
}