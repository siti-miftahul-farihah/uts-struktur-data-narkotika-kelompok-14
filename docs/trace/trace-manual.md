# Trace Manual Operasi Struktur Data

Dokumen ini mencatat penelusuran (*trace*) langkah demi langkah pada struktur utama `SinglyLinkedList<PutusanNarkotika>` dan struktur sekunder `AuditStack<String>` berdasarkan skenario pengujian pada Bagian 4.1[cite: 14, 15].

## 1. Trace Pencarian (`cariPutusan`) pada 50 Record
Dataset awal dimuat dari `data/putusan-50.csv` (`size = 50`, `head = P001`, `tail = P050`)[cite: 15].

| Input Pencarian | Iterasi Node yang Diperiksa | Jumlah Langkah | Hasil |
| :--- | :--- | :---: | :--- |
| `"P001"` | Node[0] (`P001`) -> Cocok | 1 | Ditemukan (`P001`) |
| `"P025"` | Node[0] (`P001`) .. Node[24] (`P025`) -> Cocok | 25 | Ditemukan (`P025`) |
| `"P050"` | Node[0] (`P001`) .. Node[49] (`P050`) -> Cocok | 50 | Ditemukan (`P050`) |
| `"P999"` | Node[0] (`P001`) .. Node[49] (`P050`) -> `null` | 50 | Tidak Ditemukan (`null`) |

## 2. Trace Penghapusan & Penambahan Kembali (`remove` & `addLast`)
Misalkan kondisi awal list memiliki 5 node: `[P001] -> [P002] -> [P003] -> [P004] -> [P005] -> null` (`head = P001`, `tail = P005`, `size = 5`)[cite: 14, 15].

1. **Hapus Tengah (`remove(2)` -> `P003`)**:
    - Traversal `prev` berhenti pada indeks `1` (`P002`).
    - `prev.next` diubah dari `P003` menjadi `P004`.
    - Kondisi list: `[P001] -> [P002] -> [P004] -> [P005] -> null` (`head = P001`, `tail = P005`, `size = 4`).
2. **Hapus Awal (`remove(0)` -> `P001`)**:
    - `head` digeser ke `head.next` (`P002`).
    - Kondisi list: `[P002] -> [P004] -> [P005] -> null` (`head = P002`, `tail = P005`, `size = 3`).
3. **Hapus Akhir (`remove(2)` -> `P005`)**:
    - Traversal `prev` berhenti pada indeks `1` (`P004`).
    - `prev.next` menjadi `null` dan pointer `tail` dipindahkan ke `P004`.
    - Kondisi list: `[P002] -> [P004] -> null` (`head = P002`, `tail = P004`, `size = 2`).
4. **Tambah Kembali 3 Record (`P006`, `P007`, `P008`)**:
    - Setiap pemanggilan `addLast` menyambungkan `tail.next = newNode` dan memindahkan `tail = newNode` ($O(1)$)[cite: 14, 15].
    - Kondisi akhir: `[P002] -> [P004] -> [P006] -> [P007] -> [P008] -> null` (`head = P002`, `tail = P008`, `size = 5`, `validateInvariant()` valid)[cite: 14, 15].

## 3. Trace Pencarian Non-Destruktif pada `AuditStack<String>`
Keadaan awal `AuditStack` (dari `top` ke bawah): `LOG-4` -> `LOG-3` -> `LOG-2` -> `LOG-1` (`size = 4`)[cite: 14, 15].
- Dipanggil `searchPreserve("LOG-2")`[cite: 14]:
    1. `pop()` `LOG-4` (posisi 1, bukan target) -> `tempStack.push("LOG-4")`[cite: 14].
    2. `pop()` `LOG-3` (posisi 2, bukan target) -> `tempStack.push("LOG-3")`[cite: 14].
    3. `pop()` `LOG-2` (posisi 3, **cocok**, simpan `foundPosition = 3`) -> `tempStack.push("LOG-2")`[cite: 14].
    4. `pop()` `LOG-1` (posisi 4, bukan target) -> `tempStack.push("LOG-1")`[cite: 14].
    5. `restore(tempStack)` memindahkan kembali `LOG-1`, `LOG-2`, `LOG-3`, `LOG-4` ke stack utama[cite: 14].
- **Hasil akhir**: Mengembalikan `3`, dan urutan stack kembali identik: `LOG-4` -> `LOG-3` -> `LOG-2` -> `LOG-1`[cite: 15].