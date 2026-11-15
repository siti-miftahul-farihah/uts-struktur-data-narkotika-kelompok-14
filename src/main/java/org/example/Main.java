package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PutusanLinkedList listAwal;

        try {
            listAwal = PutusanCsvParser.parseFile("data/putusan-50.csv");
        } catch (IOException e) {
            System.out.println("Gagal membaca dataset awal: " + e.getMessage());
            listAwal = new PutusanLinkedList();
        }

        PutusanService service = new PutusanService(listAwal);
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n=== APLIKASI ANALISIS PUTUSAN NARKOTIKA (SINGLY LINKED LIST) ===");
            System.out.println("Total data saat ini: " + service.getDaftarPutusan().size() + " putusan");
            System.out.println("1. Tampilkan Semua Putusan");
            System.out.println("2. Cari Putusan (caseId / Nomor Perkara + Hitung Langkah)");
            System.out.println("3. Filter Gabungan (Jenis, Berat, Pasal, Vonis)");
            System.out.println("4. Tampilkan Statistik & Agregasi");
            System.out.println("5. Urutkan Berdasarkan Vonis (Merge Sort)");
            System.out.println("6. Tambah Putusan Baru");
            System.out.println("7. Hapus Putusan Berdasarkan Posisi Indeks");
            System.out.println("8. Lihat Riwayat Audit (AuditStack)");
            System.out.println("9. Ekspor Data ke File CSV");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = bacaBaris(reader).trim();

            switch (pilihan) {
                case "1":
                    tampilkanList(service.getDaftarPutusan());
                    service.catatAudit("TAMPIL: Menampilkan seluruh " + service.getDaftarPutusan().size() + " putusan");
                    break;

                case "2":
                    System.out.print("Masukkan caseId (misal P025) atau Nomor Perkara: ");
                    String keyword = bacaBaris(reader);
                    PutusanService.SearchResult hasilCari = service.cariPutusan(keyword);
                    if (hasilCari.getPutusan() != null) {
                        System.out.println("DITEMUKAN dalam " + hasilCari.getSteps() + " langkah:");
                        System.out.println("  -> " + hasilCari.getPutusan());
                    } else {
                        System.out.println("TIDAK DITEMUKAN setelah memeriksa " + hasilCari.getSteps() + " langkah.");
                    }
                    break;

                case "3":
                    System.out.print("Jenis Narkotika (kosongkan jika semua): ");
                    String jenis = bacaBaris(reader);
                    System.out.print("Berat Minimum gram (kosongkan jika bebas): ");
                    Double minBerat = parseDoubleNullable(bacaBaris(reader));
                    System.out.print("Berat Maksimum gram (kosongkan jika bebas): ");
                    Double maxBerat = parseDoubleNullable(bacaBaris(reader));
                    System.out.print("Pasal Terbukti (kosongkan jika semua): ");
                    String pasal = bacaBaris(reader);
                    System.out.print("Vonis Minimum bulan (kosongkan jika bebas): ");
                    Integer minVonis = parseIntNullable(bacaBaris(reader));
                    System.out.print("Vonis Maksimum bulan (kosongkan jika bebas): ");
                    Integer maxVonis = parseIntNullable(bacaBaris(reader));

                    PutusanLinkedList hasilFilter = service.filterGabungan(jenis, minBerat, maxBerat, pasal, minVonis, maxVonis);
                    System.out.println("Hasil Filter (" + hasilFilter.size() + " putusan):");
                    tampilkanList(hasilFilter);
                    break;

                case "4":
                    PutusanService.AggregationResult agg = service.hitungAgregasi();
                    System.out.println("\n--- RINGKASAN AGREGASI ---");
                    System.out.println("Jumlah Perkara   : " + agg.getTotalPerkara());
                    System.out.printf("Rata-rata Vonis  : %.2f bulan%n", agg.getRataRataVonisBulan());
                    System.out.println("Total Denda      : Rp " + agg.getTotalDendaRupiah());
                    System.out.println("Distribusi Jenis Narkotika:");
                    for (int i = 0; i < agg.getJumlahJenisUnik(); i++) {
                        System.out.println("  - " + agg.getDaftarJenis()[i] + ": " + agg.getJumlahPerJenis()[i] + " perkara");
                    }
                    break;

                case "5":
                    System.out.print("Urutkan dari (1) Terkecil ke Terbesar, atau (2) Terbesar ke Terkecil? ");
                    boolean asc = !"2".equals(bacaBaris(reader).trim());
                    PutusanLinkedList terurut = service.urutkanBerdasarkanVonis(asc);
                    tampilkanList(terurut);
                    break;

                case "6":
                    try {
                        System.out.print("Case ID (misal P051): ");
                        String id = bacaBaris(reader).trim();
                        System.out.print("Nomor Perkara: ");
                        String noPerkara = bacaBaris(reader).trim();
                        System.out.print("Pengadilan: ");
                        String pengadilan = bacaBaris(reader).trim();
                        System.out.print("Jenis Narkotika: ");
                        String jns = bacaBaris(reader).trim();
                        System.out.print("Berat Barang Bukti (gram): ");
                        double berat = Double.parseDouble(bacaBaris(reader).trim());
                        System.out.print("Pasal Terbukti: ");
                        String psl = bacaBaris(reader).trim();
                        System.out.print("Vonis Penjara (bulan): ");
                        int vonis = Integer.parseInt(bacaBaris(reader).trim());
                        System.out.print("Denda (Rupiah): ");
                        long denda = Long.parseLong(bacaBaris(reader).trim());

                        PutusanNarkotika baru = new PutusanNarkotika(
                                id, noPerkara, pengadilan, LocalDate.now(), "T-BARU",
                                jns, berat, psl, psl, vonis, vonis, denda,
                                "Barang bukti " + jns, "-", "-", "input-manual"
                        );
                        service.tambahPutusan(baru);
                        System.out.println("Putusan berhasil ditambahkan!");
                    } catch (Exception e) {
                        System.out.println("Input tidak valid: " + e.getMessage());
                    }
                    break;

                case "7":
                    try {
                        System.out.print("Masukkan indeks yang akan dihapus (0 s.d. " + (service.getDaftarPutusan().size() - 1) + "): ");
                        int idx = Integer.parseInt(bacaBaris(reader).trim());
                        PutusanNarkotika dihapus = service.hapusPutusan(idx);
                        System.out.println("Berhasil menghapus: " + dihapus);
                    } catch (Exception e) {
                        System.out.println("Gagal menghapus: " + e.getMessage());
                    }
                    break;

                case "8":
                    System.out.println("\n--- RIWAYAT AUDIT (TERBARU DI ATAS / LIFO) ---");
                    AuditStack<String> temp = new AuditStack<>();
                    AuditStack<String> audit = service.getRiwayatAudit();
                    int no = 1;
                    while (!audit.isEmpty()) {
                        String log = audit.pop();
                        System.out.println(no++ + ". " + log);
                        temp.push(log);
                    }
                    audit.restore(temp);
                    break;

                case "9":
                    try {
                        String path = "docs/demo/laporan-putusan.csv";
                        service.eksporKeFile(service.getDaftarPutusan(), path);
                        System.out.println("Data berhasil diekspor ke: " + path);
                    } catch (IOException e) {
                        System.out.println("Gagal ekspor: " + e.getMessage());
                    }
                    break;

                case "0":
                    berjalan = false;
                    System.out.println("Keluar dari aplikasi.");
                    break;

                default:
                    System.out.println("Pilihan tidak dikenali.");
            }
        }
    }

    private static String bacaBaris(BufferedReader reader) throws IOException {
        String baris = reader.readLine();
        return baris == null ? "" : baris;
    }

    private static void tampilkanList(PutusanLinkedList list) {
        if (list.isEmpty()) {
            System.out.println("(Tidak ada data)");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    private static Double parseDoubleNullable(String input) {
        if (input == null || input.isBlank()) return null;
        return Double.parseDouble(input.trim());
    }

    private static Integer parseIntNullable(String input) {
        if (input == null || input.isBlank()) return null;
        return Integer.parseInt(input.trim());
    }
}