# Laporan Transparansi Penggunaan AI Generatif

- **Nama**: Siti Miftahul Farihah
- **NIM**: 202510370110222
- **Kelas**: Struktur Data — Kelas B

Sesuai ketentuan kebijakan penggunaan AI pada panduan UTS Struktur Data, seluruh pemanfaatan AI generatif dicatat secara transparan dan telah diverifikasi melalui kompilasi serta pengujian JUnit 5[cite: 9].

| Tanggal | Mahasiswa | Alat | Tujuan | Ringkasan Prompt | Bagian Keluaran yang Digunakan | Perubahan & Cara Verifikasi |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 2026-11-15 | Siti Miftahul Farihah (202510370110222) | Gemini | Penjelasan error kompilasi & struktur kelas generik | Meminta analisis error penempatan package dan perbedaan parameter konstruktor pada `SinglyLinkedList<T>` dan `AuditStack<T>` | Kerangka kelas generik `StrukturData<T>`, `SinglyLinkedList<T>`, dan `AuditStack<T>` | Disesuaikan dengan skema 16 atribut `PutusanNarkotika`, ditambahkan `validateInvariant()`, dan diverifikasi melalui 11 unit test (`PutusanLinkedListTest` & `AuditStackTest`) yang lulus 100%. |
| 2026-11-15 | Siti Miftahul Farihah (202510370110222) | Gemini | Peninjauan algoritma pengurutan & I/O tanpa `java.util` | Meminta implementasi *Merge Sort* pada array mentah tanpa koleksi `java.util` serta penggantian `Scanner` dengan `BufferedReader` | Implementasi `mergeSortVonis` pada `PutusanService` dan helper pembacaan input pada `Main.java` | Diuji melalui eksekusi langsung seluruh menu CLI dan diverifikasi lewat `PutusanServiceTest` (total 15 unit test lulus tanpa error). |
| 2026-11-15 | Siti Miftahul Farihah (202510370110222) | Gemini | Penyusunan kerangka dokumentasi ADR & trace manual | Meminta contoh struktur dokumen *Architecture Decision Record* dan tabel *trace* operasi | Kerangka `docs/adr/001`, `docs/adr/002`, dan `docs/trace/trace-manual.md` | Seluruh angka langkah pencarian dan contoh kasus disesuaikan dengan dataset aktual `data/putusan-50.csv`. |