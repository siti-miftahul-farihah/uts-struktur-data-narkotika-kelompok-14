package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PutusanService {

    /**
     * Objek pembungkus hasil pencarian beserta jumlah langkah traversal node.
     */
    public static class SearchResult {
        private final PutusanNarkotika putusan;
        private final int steps;

        public SearchResult(PutusanNarkotika putusan, int steps) {
            this.putusan = putusan;
            this.steps = steps;
        }

        public PutusanNarkotika getPutusan() { return putusan; }
        public int getSteps() { return steps; }
    }

    /**
     * Objek pembungkus hasil agregasi statistik dataset putusan.
     */
    public static class AggregationResult {
        private final int totalPerkara;
        private final double rataRataVonisBulan;
        private final long totalDendaRupiah;
        private final String[] daftarJenis;
        private final int[] jumlahPerJenis;
        private final int jumlahJenisUnik;

        public AggregationResult(int totalPerkara, double rataRataVonisBulan, long totalDendaRupiah,
                                 String[] daftarJenis, int[] jumlahPerJenis, int jumlahJenisUnik) {
            this.totalPerkara = totalPerkara;
            this.rataRataVonisBulan = rataRataVonisBulan;
            this.totalDendaRupiah = totalDendaRupiah;
            this.daftarJenis = daftarJenis;
            this.jumlahPerJenis = jumlahPerJenis;
            this.jumlahJenisUnik = jumlahJenisUnik;
        }

        public int getTotalPerkara() { return totalPerkara; }
        public double getRataRataVonisBulan() { return rataRataVonisBulan; }
        public long getTotalDendaRupiah() { return totalDendaRupiah; }
        public String[] getDaftarJenis() { return daftarJenis; }
        public int[] getJumlahPerJenis() { return jumlahPerJenis; }
        public int getJumlahJenisUnik() { return jumlahJenisUnik; }
    }

    private final PutusanLinkedList daftarPutusan;
    private final AuditStack<String> riwayatAudit;

    public PutusanService(PutusanLinkedList daftarPutusan) {
        this.daftarPutusan = daftarPutusan;
        this.riwayatAudit = new AuditStack<>();
        catatAudit("INIT: Aplikasi dimulai dengan " + daftarPutusan.size() + " putusan.");
    }

    public PutusanLinkedList getDaftarPutusan() {
        return daftarPutusan;
    }

    public AuditStack<String> getRiwayatAudit() {
        return riwayatAudit;
    }

    public void catatAudit(String aktivitas) {
        riwayatAudit.push(aktivitas);
    }

    /**
     * Fitur Tambah: Menambahkan putusan baru langsung ke struktur utama.
     */
    public void tambahPutusan(PutusanNarkotika putusan) {
        daftarPutusan.addLast(putusan);
        catatAudit("TAMBAH: Menambahkan putusan " + putusan.getCaseId() + " (" + putusan.getNomorPerkara() + ")");
    }

    /**
     * Fitur Hapus: Menghapus putusan pada indeks tertentu langsung pada struktur utama.
     */
    public PutusanNarkotika hapusPutusan(int index) {
        PutusanNarkotika dihapus = daftarPutusan.remove(index);
        catatAudit("HAPUS: Menghapus putusan " + dihapus.getCaseId() + " pada posisi " + index);
        return dihapus;
    }

    /**
     * Fitur Cari: Mencari berdasarkan caseId atau nomorPerkara sekaligus menghitung jumlah langkah (O(n)).
     */
    public SearchResult cariPutusan(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            catatAudit("CARI: Kata kunci kosong (0 langkah)");
            return new SearchResult(null, 0);
        }

        String target = keyword.trim();
        int langkah = 0;

        for (int i = 0; i < daftarPutusan.size(); i++) {
            langkah++;
            PutusanNarkotika current = daftarPutusan.get(i);
            if (current.getCaseId().equalsIgnoreCase(target)
                    || current.getNomorPerkara().equalsIgnoreCase(target)) {
                catatAudit("CARI: '" + target + "' ditemukan (" + current.getCaseId() + ") dalam " + langkah + " langkah");
                return new SearchResult(current, langkah);
            }
        }

        catatAudit("CARI: '" + target + "' tidak ditemukan setelah " + langkah + " langkah");
        return new SearchResult(null, langkah);
    }

    /**
     * Fitur Filter Gabungan: Menyaring berdasarkan jenis narkotika, rentang berat, pasal terbukti, dan rentang vonis.
     * Parameter null atau nilai negatif untuk batas akan diabaikan (tidak memfilter kriteria tersebut).
     */
    public PutusanLinkedList filterGabungan(
            String jenisNarkotika,
            Double minBerat,
            Double maxBerat,
            String pasalTerbukti,
            Integer minVonis,
            Integer maxVonis
    ) {
        PutusanLinkedList hasil = new PutusanLinkedList();

        for (int i = 0; i < daftarPutusan.size(); i++) {
            PutusanNarkotika p = daftarPutusan.get(i);

            if (jenisNarkotika != null && !jenisNarkotika.isBlank()) {
                if (p.getJenisNarkotika() == null || !p.getJenisNarkotika().toLowerCase().contains(jenisNarkotika.trim().toLowerCase())) {
                    continue;
                }
            }
            if (minBerat != null && p.getBeratBarangBuktiGram() < minBerat) {
                continue;
            }
            if (maxBerat != null && p.getBeratBarangBuktiGram() > maxBerat) {
                continue;
            }
            if (pasalTerbukti != null && !pasalTerbukti.isBlank()) {
                if (p.getPasalTerbukti() == null || !p.getPasalTerbukti().toLowerCase().contains(pasalTerbukti.trim().toLowerCase())) {
                    continue;
                }
            }
            if (minVonis != null && p.getVonisBulan() < minVonis) {
                continue;
            }
            if (maxVonis != null && p.getVonisBulan() > maxVonis) {
                continue;
            }

            hasil.addLast(p);
        }

        catatAudit("FILTER: Ditemukan " + hasil.size() + " putusan yang cocok dengan kriteria gabungan");
        return hasil;
    }

    /**
     * Fitur Agregasi: Menghitung total perkara, rata-rata vonis, total denda, dan distribusi jenis narkotika
     * menggunakan array mentah tanpa koleksi java.util.
     */
    public AggregationResult hitungAgregasi() {
        int n = daftarPutusan.size();
        if (n == 0) {
            return new AggregationResult(0, 0.0, 0L, new String[0], new int[0], 0);
        }

        long totalVonis = 0;
        long totalDenda = 0;
        String[] jenisArr = new String[n];
        int[] countArr = new int[n];
        int uniqueCount = 0;

        for (int i = 0; i < n; i++) {
            PutusanNarkotika p = daftarPutusan.get(i);
            totalVonis += p.getVonisBulan();
            totalDenda += p.getDendaRupiah();

            String jenis = (p.getJenisNarkotika() == null || p.getJenisNarkotika().isBlank())
                    ? "Tidak Diketahui" : p.getJenisNarkotika().trim();

            int foundIdx = -1;
            for (int j = 0; j < uniqueCount; j++) {
                if (jenisArr[j].equalsIgnoreCase(jenis)) {
                    foundIdx = j;
                    break;
                }
            }

            if (foundIdx != -1) {
                countArr[foundIdx]++;
            } else {
                jenisArr[uniqueCount] = jenis;
                countArr[uniqueCount] = 1;
                uniqueCount++;
            }
        }

        double rataVonis = (double) totalVonis / n;
        catatAudit("AGREGASI: Dihitung untuk " + n + " perkara (rata-rata vonis " + String.format("%.2f", rataVonis) + " bln)");
        return new AggregationResult(n, rataVonis, totalDenda, jenisArr, countArr, uniqueCount);
    }

    /**
     * Fitur Urutan: Mengurutkan putusan berdasarkan vonisBulan menggunakan Merge Sort pada array mentah
     * dan mengembalikan PutusanLinkedList baru tanpa merusak invariant struktur utama.
     */
    public PutusanLinkedList urutkanBerdasarkanVonis(boolean ascending) {
        int n = daftarPutusan.size();
        PutusanNarkotika[] arr = new PutusanNarkotika[n];
        for (int i = 0; i < n; i++) {
            arr[i] = daftarPutusan.get(i);
        }

        if (n > 1) {
            mergeSortVonis(arr, 0, n - 1, ascending);
        }

        PutusanLinkedList terurut = new PutusanLinkedList();
        for (int i = 0; i < n; i++) {
            terurut.addLast(arr[i]);
        }

        daftarPutusan.validateInvariant();
        terurut.validateInvariant();
        catatAudit("URUTAN: Mengurutkan " + n + " putusan berdasarkan vonis (" + (ascending ? "Terkecil -> Terbesar" : "Terbesar -> Terkecil") + ")");
        return terurut;
    }

    private void mergeSortVonis(PutusanNarkotika[] arr, int left, int right, boolean ascending) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSortVonis(arr, left, mid, ascending);
        mergeSortVonis(arr, mid + 1, right, ascending);
        mergeVonis(arr, left, mid, right, ascending);
    }

    private void mergeVonis(PutusanNarkotika[] arr, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        PutusanNarkotika[] L = new PutusanNarkotika[n1];
        PutusanNarkotika[] R = new PutusanNarkotika[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            boolean pilihKiri = ascending
                    ? (L[i].getVonisBulan() <= R[j].getVonisBulan())
                    : (L[i].getVonisBulan() >= R[j].getVonisBulan());
            if (pilihKiri) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    /**
     * Fitur Ekspor: Menyimpan daftar putusan ke file CSV/TXT.
     */
    public void eksporKeFile(PutusanLinkedList dataEkspor, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("case_id,nomor_perkara,pengadilan,jenis_narkotika,berat_gram,pasal_terbukti,vonis_bulan,denda_rupiah");
            writer.newLine();
            for (int i = 0; i < dataEkspor.size(); i++) {
                PutusanNarkotika p = dataEkspor.get(i);
                writer.write(String.format("%s,\"%s\",\"%s\",\"%s\",%.3f,\"%s\",%d,%d",
                        p.getCaseId(),
                        p.getNomorPerkara(),
                        p.getPengadilan(),
                        p.getJenisNarkotika(),
                        p.getBeratBarangBuktiGram(),
                        p.getPasalTerbukti(),
                        p.getVonisBulan(),
                        p.getDendaRupiah()
                ));
                writer.newLine();
            }
        }
        catatAudit("EKSPOR: Menyimpan " + dataEkspor.size() + " record ke " + outputPath);
    }
}