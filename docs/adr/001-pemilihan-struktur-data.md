# ADR-001: Pemilihan Struktur Data Utama dan Sekunder

## Status
Diterima (Accepted)

## Konteks
Aplikasi perlu menyimpan dan memanipulasi 50 record putusan pengadilan narkotika dengan skema 16 atribut tanpa menggunakan koleksi bawaan dari `java.util` (`ArrayList`, `LinkedList`, `Stack`, `Queue`, dll.)[cite: 24, 25]. Operasi yang dibutuhkan meliputi penambahan di awal/akhir/indeks tertentu, penghapusan dinamis, traversal berurutan, serta pencatatan riwayat aktivitas pengguna secara LIFO (*Last-In, First-Out*)[cite: 25, 26].

## Keputusan
1. **Struktur Data Utama — `SinglyLinkedList<T>` (dan turunannya `PutusanLinkedList`)**:
    - Mengimplementasikan antarmuka generik `StrukturData<T>` menggunakan node berantai dengan pointer `head` dan `tail`[cite: 25].
    - Operasi `addFirst` dan `addLast` berjalan dalam waktu $O(1)$ berkat penyimpanan pointer `tail`, sedangkan `insert`, `get`, dan `remove` berjalan dalam $O(n)$ tanpa perlu realokasi ukuran array seperti pada array dinamis[cite: 25].
    - Setiap operasi mutasi memanggil `validateInvariant()` untuk menjamin `head == null && tail == null` saat kosong, `head == tail` saat berukuran 1, dan `tail.next == null` saat berukuran $>1$[cite: 25].

2. **Struktur Data Sekunder — `AuditStack<T>`**:
    - Dibangun dari nol menggunakan node berantai generik dengan prinsip LIFO untuk mencatat log operasi pengguna terbaru di puncak tumpukan (*top*) dalam waktu $O(1)$[cite: 25, 26].
    - Menyediakan `searchPreserve` dan `restore` menggunakan stack sementara agar pencarian riwayat tidak merusak urutan LIFO asli[cite: 25, 26].

## Konsekuensi
- **Positif**: Penyisipan dan penghapusan node tidak memerlukan pergeseran elemen memori, dan konsistensi struktur terjamin otomatis lewat `validateInvariant()`[cite: 25].
- **Negatif**: Akses acak berdasarkan indeks (`get(index)`) membutuhkan traversal sekuensial dari `head` sehingga memiliki kompleksitas $O(n)$[cite: 25].