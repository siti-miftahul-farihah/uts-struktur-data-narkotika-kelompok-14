# ADR-002: Strategi Pencarian Berpenghitung Langkah dan Pengurutan Non-Destruktif

## Status
Diterima (Accepted)

## Konteks
Aplikasi mewajibkan fitur pencarian berdasarkan `caseId` atau `nomorPerkara` dengan pelaporan jumlah langkah traversal, filter gabungan multi-kriteria, serta pengurutan putusan berdasarkan `vonisBulan` tanpa merusak urutan atau invariant pada struktur data utama.

## Keputusan
1. **Pencarian Linier dengan Pencacah Langkah (`cariPutusan`)**:
    - Karena `SinglyLinkedList<T>` tidak memiliki pengindeksan memori langsung dan data awal diurutkan berdasarkan `caseId` (bukan `nomorPerkara`), digunakan pencarian sekuensial (*Linear Search*) dari indeks `0` hingga `size - 1`[cite: 25, 26].
    - Kompleksitas waktu: $O(1)$ pada kasus terbaik (elemen pertama), $O(n)$ pada kasus terburuk atau elemen tidak ditemukan. Setiap iterasi menaikkan pencacah `langkah` dan mengembalikannya dalam objek `SearchResult`.

2. **Pengurutan Merge Sort pada Array Mentah (`urutkanBerdasarkanVonis`)**:
    - Elemen dari `PutusanLinkedList` disalin ke array mentah sementara `PutusanNarkotika[]`, diurutkan menggunakan algoritma *Merge Sort* buatan sendiri dengan kompleksitas $O(n \log n)$ yang stabil, lalu dimasukkan ke instance `PutusanLinkedList` baru[cite: 25, 26].
    - Struktur utama asli (`daftarPutusan`) tidak dimodifikasi sehingga urutan asli dan `validateInvariant()` tetap terjaga 100%.

## Konsekuensi
- **Positif**: Kompleksitas pengurutan terjamin $O(n \log n)$ pada semua kondisi data dan bebas dari risiko kerusakan pointer `head`/`tail` struktur utama[cite: 25, 26].
- **Negatif**: Membutuhkan alokasi memori tambahan sebesar $O(n)$ untuk array bantu selama proses pengurutan berlangsung.