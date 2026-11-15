# Catatan Kontribusi & Riwayat Pengembangan

- **Nama**: Siti Miftahul Farihah
- **NIM**: 202510370110222
- **Kelas**: Struktur Data — Kelas B
- **Keterangan**: Seluruh tahapan ekstraksi dataset, perancangan struktur data, implementasi fitur, pengujian otomatis, hingga dokumentasi dikerjakan secara mandiri dengan menerapkan alur *feature branch*, *pull request*, dan *self-review* pada repositori GitHub.

## Daftar Tahapan & Pull Request

| Tahap / Area | Branch | Ringkasan Kontribusi Teknis |
| :--- | :--- | :--- |
| **1. Dataset & Parser CSV** | `feat/dataset-dan-parser` | Mengekstrak 50 putusan pengadilan narkotika ke `data/putusan-50.csv`, menyusun metadata atribusi per bidang pada `docs/data-provenance.csv`, dan mengimplementasikan `PutusanCsvParser` beserta unit test validasi input tidak valid. |
| **2. Model & Struktur Data** | `feat/model-dan-struktur-data` | Mengimplementasikan kelas domain 16 parameter `PutusanNarkotika`, antarmuka generik `StrukturData<T>`, struktur utama `SinglyLinkedList<T>` (`PutusanLinkedList`), struktur sekunder `AuditStack<T>` (`searchPreserve` & `restore`), serta 11 unit test (`PutusanLinkedListTest` & `AuditStackTest`). |
| **3. Fitur & Algoritma Aplikasi** | `feat/fitur-dan-algoritma` | Membangun `PutusanService` (pencarian berpenghitung langkah, filter gabungan multi-kriteria, agregasi statistik, pengurutan *Merge Sort* vonis non-destruktif, pencatatan audit, dan ekspor CSV), antarmuka CLI `Main.java`, serta 4 unit test (`PutusanServiceTest`). |
| **4. Refactoring Bebas `java.util`** | `refactor/hapus-scanner-main` (PR #7) | Mengganti `java.util.Scanner` pada `Main.java` dengan `java.io.BufferedReader` agar seluruh kelas kode produksi di `src/main/java` bebas dari impor `java.util.*`. |
| **5. Dokumentasi & Rilis Final** | `docs/dokumentasi-dan-trace` | Menyusun 2 dokumen ADR (`docs/adr/001` & `docs/adr/002`), penelusuran langkah manual (`docs/trace/trace-manual.md`), contoh ekspor CSV (`docs/demo/laporan-putusan.csv`), `README.md`, `CONTRIBUTIONS.md`, `AI_USAGE.md`, serta tag rilis `uts-v1.0`. |