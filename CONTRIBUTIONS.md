# Catatan Kontribusi & Riwayat Pengembangan

- **Nama**: Siti Miftahul Farihah
- **NIM**: 202510370110222
- **Kelas**: Struktur Data — Kelas B
- **Repositori**: [uts-struktur-data-narkotika-kelompok-14](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14)
- **Keterangan**: Seluruh tahapan ekstraksi dataset, perancangan struktur data, implementasi fitur, pengujian otomatis, hingga dokumentasi dikerjakan secara mandiri dengan menerapkan alur *feature branch*, *pull request*, dan *self-review* pada repositori GitHub.

## Daftar Tahapan, Issue, & Pull Request

| Tahap / Area | Tautan PR & Issue | Ringkasan Kontribusi Teknis |
| :--- | :--- | :--- |
| **1. Model Awal & Struktur Dasar** | [PR #2](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/2), [Issue #1](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/issues/1) | Membangun kerangka awal model `PutusanNarkotika` dan struktur dasar `PutusanLinkedList`. |
| **2. Dataset & Parser CSV** | [PR #4](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/4) | Mengekstrak 50 putusan pengadilan narkotika ke `data/putusan-50.csv`, menyusun metadata atribusi per bidang pada `docs/data-provenance.csv`, dan mengimplementasikan `PutusanCsvParser` beserta unit test validasi input tidak valid. |
| **3. Struktur Data Utama & Sekunder** | [PR #5](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/5) | Mengimplementasikan kelas domain 16 parameter `PutusanNarkotika`, antarmuka generik `StrukturData<T>`, struktur utama `SinglyLinkedList<T>` (`PutusanLinkedList`), struktur sekunder `AuditStack<T>` (`searchPreserve` & `restore`), serta 11 unit test (`PutusanLinkedListTest` & `AuditStackTest`). |
| **4. Fitur & Algoritma Aplikasi** | [PR #6](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/6) | Membangun `PutusanService` (pencarian berpenghitung langkah, filter gabungan multi-kriteria, agregasi statistik, pengurutan *Merge Sort* vonis non-destruktif, pencatatan audit, dan ekspor CSV), antarmuka CLI `Main.java`, serta 4 unit test (`PutusanServiceTest`). |
| **5. Refactoring Bebas `java.util`** | [PR #7](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/7) | Mengganti `java.util.Scanner` pada `Main.java` dengan `java.io.BufferedReader` agar seluruh kelas kode produksi di `src/main/java` bebas dari impor `java.util.*`. |
| **6. Dokumentasi & Rilis Final** | [PR #8](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/8), [PR #9](https://github.com/siti-miftahul-farihah/uts-struktur-data-narkotika-kelompok-14/pull/9) | Menyusun 2 dokumen ADR (`docs/adr/001` & `docs/adr/002`), penelusuran langkah manual (`docs/trace/trace-manual.md`), contoh ekspor CSV (`docs/demo/laporan-putusan.csv`), `README.md`, `CONTRIBUTIONS.md`, `AI_USAGE.md`, serta tag rilis `uts-v1.0`. |
