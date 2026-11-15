# Aplikasi Analisis Putusan Pengadilan Narkotika (UTS Struktur Data)

Aplikasi Java berorientasi objek berbasis CLI untuk mengelola, menyaring, mengurutkan, dan menganalisis 50 putusan pengadilan narkotika menggunakan implementasi struktur data kustom dari nol tanpa koleksi bawaan `java.util`.

## Identitas Mahasiswa
- **Nama**: Siti Miftahul Farihah
- **NIM**: 202510370110222
- **Kelas**: Struktur Data — Kelas B
- **Mode Pengerjaan**: Mandiri (Individu)
- **Program Studi / Fakultas**: Informatika, Fakultas Teknik — Universitas Muhammadiyah Malang
- **Dosen Pengampu**: Galih W. Wicaksono
- **Repository**: `uts-struktur-data-narkotika-kelompok-14`

## Pilihan Struktur Data
1. **Struktur Data Utama — `SinglyLinkedList<T>` (`PutusanLinkedList`)**:
    - Mengimplementasikan antarmuka generik `StrukturData<T>` menggunakan node berantai dengan pointer `head` dan `tail`.
    - Menyimpan seluruh 50 objek `PutusanNarkotika` (16 atribut) secara dinamis dengan operasi `addFirst`, `addLast`, `insert`, `get`, `remove`, dan `search`, serta pemeriksaan konsistensi pointer otomatis melalui `validateInvariant()`.
2. **Struktur Data Sekunder — `AuditStack<T>`**:
    - Tumpukan LIFO generik berbasis node berantai untuk merekam riwayat aktivitas pengguna.
    - Dilengkapi operasi `push`, `pop`, `peek`, serta pencarian non-destruktif `searchPreserve()` dan `restore()` agar urutan LIFO tetap terjaga setelah pencarian.

## Cara Build, Test, dan Menjalankan Aplikasi
Pastikan JDK 17+ dan Apache Maven telah terpasang di perangkat.

1. **Kompilasi proyek**:
   ```bash
   mvn compile