package org.example;

public class PutusanNarkotika {
    private final String idPutusan;
    private final String nomorPutusan;
    private final String pengadilan;
    private final int tahunPutus;
    private final String jenisNarkotika;
    private final double beratGram;
    private final String pasalDakwaan;
    private final int lamaPidanaBulan;
    private final long dendaRupiah;
    private final String statusHukum;

    public PutusanNarkotika(
            String idPutusan,
            String nomorPutusan,
            String pengadilan,
            int tahunPutus,
            String jenisNarkotika,
            double beratGram,
            String pasalDakwaan,
            int lamaPidanaBulan,
            long dendaRupiah,
            String statusHukum
    ) {
        if (idPutusan == null || idPutusan.isBlank()) {
            throw new IllegalArgumentException("ID putusan tidak boleh kosong.");
        }
        if (nomorPutusan == null || nomorPutusan.isBlank()) {
            throw new IllegalArgumentException("Nomor putusan tidak boleh kosong.");
        }
        if (beratGram < 0 || lamaPidanaBulan < 0 || dendaRupiah < 0) {
            throw new IllegalArgumentException("Berat, lama pidana, dan denda tidak boleh negatif.");
        }

        this.idPutusan = idPutusan;
        this.nomorPutusan = nomorPutusan;
        this.pengadilan = pengadilan;
        this.tahunPutus = tahunPutus;
        this.jenisNarkotika = jenisNarkotika;
        this.beratGram = beratGram;
        this.pasalDakwaan = pasalDakwaan;
        this.lamaPidanaBulan = lamaPidanaBulan;
        this.dendaRupiah = dendaRupiah;
        this.statusHukum = statusHukum;
    }

    public String getIdPutusan() { return idPutusan; }
    public String getNomorPutusan() { return nomorPutusan; }
    public String getPengadilan() { return pengadilan; }
    public int getTahunPutus() { return tahunPutus; }
    public String getJenisNarkotika() { return jenisNarkotika; }
    public double getBeratGram() { return beratGram; }
    public String getPasalDakwaan() { return pasalDakwaan; }
    public int getLamaPidanaBulan() { return lamaPidanaBulan; }
    public long getDendaRupiah() { return dendaRupiah; }
    public String getStatusHukum() { return statusHukum; }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%.2f gr) | Pidana: %d bln",
                idPutusan, nomorPutusan, jenisNarkotika, beratGram, lamaPidanaBulan);
    }
}