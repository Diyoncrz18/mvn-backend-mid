# Penjelasan Contoh Relasi Database JPA

## Tujuan
Project ini dibuat untuk menunjukkan penggunaan relasi database pada Spring Boot dengan JPA, khususnya:

- `One To Many`
- `Many To Many`

Selain itu, project ini juga sekaligus memperlihatkan relasi `Many To One` sebagai pasangan dari `One To Many`.

---

## Teknologi yang Dipakai

- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok

Database yang dipakai saat ini adalah H2 in-memory, jadi tabel akan dibuat otomatis saat aplikasi dijalankan dan data contoh akan langsung diisi oleh `DataInitializer`.

---

## Entity yang Digunakan

Project ini memakai 4 entity utama:

1. `Dosen`
2. `Mahasiswa`
3. `Book`
4. `Konsultasi`

Masing-masing entity mewakili tabel di database.

---

## Relasi yang Dibuat

### 1. Relasi One To Many

Relasi ini terjadi antara:

- `Dosen`
- `Mahasiswa`

Artinya:

- Satu dosen dapat membimbing banyak mahasiswa.
- Satu mahasiswa hanya memiliki satu dosen wali.

Secara konsep:

```text
Dosen (1) -------- (N) Mahasiswa
```

### Implementasi pada code

Pada entity `Dosen`:

```java
@OneToMany(mappedBy = "dosenWali")
private List<Mahasiswa> mahasiswaDibimbing = new ArrayList<>();
```

Penjelasan:

- `@OneToMany` berarti satu `Dosen` terhubung ke banyak `Mahasiswa`.
- `mappedBy = "dosenWali"` berarti relasi ini dikendalikan dari sisi `Mahasiswa`.

Pada entity `Mahasiswa`:

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "dosen_id")
private Dosen dosenWali;
```

Penjelasan:

- `@ManyToOne` berarti banyak mahasiswa dapat mengarah ke satu dosen.
- `@JoinColumn(name = "dosen_id")` berarti tabel `mahasiswa` memiliki foreign key bernama `dosen_id`.

### Bentuk tabel database

Tabel `dosen`:

```text
id | nama | keahlian
```

Tabel `mahasiswa`:

```text
id | nama | jurusan | dosen_id
```

Kolom `dosen_id` pada tabel `mahasiswa` menunjuk ke `id` pada tabel `dosen`.

### Contoh data

- `D01` membimbing `M01`
- `D01` membimbing `M02`
- `D03` membimbing `M03`
- `D04` membimbing `M04`

Jadi:

- satu dosen bisa punya banyak mahasiswa
- satu mahasiswa hanya punya satu dosen wali

---

## 2. Relasi Many To Many

Relasi ini terjadi antara:

- `Mahasiswa`
- `Book`

Artinya:

- Satu mahasiswa dapat memiliki banyak book.
- Satu book dapat dimiliki atau digunakan banyak mahasiswa.

Secara konsep:

```text
Mahasiswa (N) -------- (N) Book
```

### Implementasi pada code

Pada entity `Mahasiswa`:

```java
@ManyToMany
@JoinTable(
    name = "mahasiswa_book",
    joinColumns = @JoinColumn(name = "mahasiswa_id"),
    inverseJoinColumns = @JoinColumn(name = "book_id"))
private Set<Book> books = new LinkedHashSet<>();
```

Penjelasan:

- `@ManyToMany` berarti banyak mahasiswa dapat terhubung dengan banyak book.
- `@JoinTable` membuat tabel penghubung bernama `mahasiswa_book`.
- `mahasiswa_id` adalah foreign key ke tabel `mahasiswa`.
- `book_id` adalah foreign key ke tabel `book`.

Pada entity `Book`:

```java
@ManyToMany(mappedBy = "books")
private Set<Mahasiswa> mahasiswa = new LinkedHashSet<>();
```

Penjelasan:

- sisi `Book` adalah sisi kebalikan dari relasi
- `mappedBy = "books"` berarti relasinya dikelola dari sisi `Mahasiswa`

### Bentuk tabel database

Tabel `book`:

```text
id | title | author | isbn
```

Tabel penghubung `mahasiswa_book`:

```text
mahasiswa_id | book_id
```

Karena relasinya banyak ke banyak, JPA memerlukan tabel penghubung khusus.

### Contoh data

- `M01` memiliki book `1` dan `2`
- `M02` memiliki book `2`
- `M03` memiliki book `1` dan `3`
- `M04` memiliki book `3`

Artinya:

- book `2` dipakai oleh lebih dari satu mahasiswa
- mahasiswa `M01` punya lebih dari satu book

Itulah alasan relasi ini disebut `Many To Many`.

---

## 3. Relasi pada Entity Konsultasi

Walaupun fokus tugas ada pada `One To Many` dan `Many To Many`, project ini juga punya entity `Konsultasi`.

Relasinya:

- banyak konsultasi bisa dimiliki satu mahasiswa
- banyak konsultasi bisa dimiliki satu dosen

Pada entity `Konsultasi`:

```java
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "mahasiswa_id", nullable = false)
private Mahasiswa mahasiswa;

@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "dosen_id", nullable = false)
private Dosen dosen;
```

Penjelasan:

- satu data konsultasi hanya milik satu mahasiswa dan satu dosen
- tetapi satu mahasiswa dan satu dosen bisa muncul berkali-kali pada banyak data konsultasi

---

## Alur Saat Menambah Mahasiswa

Saat endpoint `POST /api/mahasiswa` dipanggil, prosesnya seperti ini:

1. Data request diterima oleh `MahasiswaController`
2. Controller memanggil `MahasiswaService`
3. Service memvalidasi isi data
4. `dosenId` dicari ke tabel `dosen`
5. `bookIds` dicari ke tabel `book`
6. Jika semua data ditemukan, entity `Mahasiswa` disimpan
7. Relasi ke dosen dan book ikut tersimpan oleh JPA

Contoh request:

```json
{
  "id": "M05",
  "nama": "Rina",
  "jurusan": "Teknik Informatika",
  "dosenId": "D01",
  "bookIds": [1, 3]
}
```

Maknanya:

- mahasiswa `M05` punya dosen wali `D01`
- mahasiswa `M05` terhubung dengan book `1` dan `3`

---

## Kenapa Menggunakan DTO Response

Project ini tidak langsung mengembalikan entity mentah ke client, tetapi memakai mapper dan response DTO.

Tujuannya:

- menghindari JSON berputar terus karena relasi dua arah
- membuat response lebih rapi
- hanya menampilkan data yang penting

Contoh:

- response dosen menampilkan daftar mahasiswa bimbingan secara ringkas
- response mahasiswa menampilkan dosen wali dan daftar book secara ringkas

---

## Data Awal yang Disediakan

Data contoh dimasukkan otomatis melalui `DataInitializer`.

Data tersebut dibuat agar relasi mudah dilihat:

- 4 dosen
- 4 mahasiswa
- 3 book
- 3 konsultasi

Contoh relasi yang langsung terlihat:

- `D01` membimbing `M01` dan `M02`
- `M01` memiliki book `1` dan `2`
- `M02` juga memiliki book `2`

Dengan data ini, relasi `One To Many` dan `Many To Many` bisa langsung diuji tanpa perlu input manual dari awal.

---

## Endpoint yang Bisa Dicoba

### Dosen

- `GET /api/dosen`
- `GET /api/dosen/{id}`
- `POST /api/dosen`
- `PUT /api/dosen?id=D01`
- `DELETE /api/dosen?id=D01`

### Mahasiswa

- `GET /api/mahasiswa`
- `GET /api/mahasiswa/{id}`
- `POST /api/mahasiswa`
- `PUT /api/mahasiswa?id=M01`
- `DELETE /api/mahasiswa?id=M01`

### Book

- `GET /api/books`
- `GET /api/books/{id}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

### Konsultasi

- `GET /api/konsultasi`
- `GET /api/konsultasi/{id}`
- `POST /api/konsultasi`
- `PUT /api/konsultasi/{id}`
- `DELETE /api/konsultasi/{id}`

---

## Hasil Tabel yang Dibentuk JPA

Saat aplikasi berjalan, JPA membuat tabel berikut:

- `dosen`
- `mahasiswa`
- `book`
- `konsultasi`
- `mahasiswa_book`

Maknanya:

- `dosen_id` di tabel `mahasiswa` menunjukkan relasi `One To Many`
- tabel `mahasiswa_book` menunjukkan relasi `Many To Many`

---

## Pengujian

Project ini juga sudah diuji dengan unit/integration test sederhana.

Hal yang diuji:

- aplikasi berhasil start
- relasi `One To Many` berjalan
- relasi `Many To Many` berjalan

Contoh pengujian:

- dosen `D01` harus memiliki mahasiswa `M01` dan `M02`
- mahasiswa `M01` harus memiliki book `1` dan `2`

---

## Kesimpulan

Dari project ini bisa dipahami bahwa:

1. Relasi `One To Many` dipakai ketika satu data induk memiliki banyak data turunan.
2. Relasi `Many To Many` dipakai ketika dua tabel saling berhubungan banyak-ke-banyak.
3. JPA mempermudah pembuatan relasi dengan annotation seperti:
   - `@OneToMany`
   - `@ManyToOne`
   - `@ManyToMany`
   - `@JoinColumn`
   - `@JoinTable`
4. Dengan JPA, kita tidak perlu menulis query SQL manual untuk membangun relasi dasar.

Jadi, contoh pada project ini sudah memenuhi kebutuhan untuk menunjukkan:

- relasi `One To Many`
- relasi `Many To Many`
- implementasi nyata menggunakan Spring Boot dan JPA

