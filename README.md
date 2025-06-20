# 🏠 Final Project - Aplikasi Manajemen Kost

📚 Proyek ini merupakan tugas akhir dari mata kuliah **Pemrograman Berorientasi Objek** yang dibangun menggunakan bahasa pemrograman **Java** dan basis data **MySQL**. Aplikasi ini berjalan berbasis antarmuka **CLI (Command Line Interface)** dan dirancang untuk membantu mengelola data penghuni kost, kamar, tagihan, serta proses login dan registrasi pengguna.

## 🎯 Tujuan Proyek

Menerapkan konsep-konsep dasar OOP Java seperti:

- 🔹 Class & Object
- 🔹 Constructor
- 🔹 Encapsulation
- 🔹 Inheritance
- 🔹 Polymorphism
- 🔹 CRUD Operations
- 🔹 JDBC (Java Database Connectivity)

## ✨ Fitur Aplikasi

### 🔐 Autentikasi
- Login untuk penghuni dan pemilik kost
- Registrasi akun baru dengan role penghuni/pemilik
- Validasi input untuk proses login/register

### 👥 Manajemen Penghuni (Pemilik)
- Melihat daftar semua penghuni
- Menambah data penghuni baru
- Mengedit data penghuni
- Menghapus data penghuni
- Mencari penghuni berdasarkan keyword
- Menempatkan penghuni ke kamar tertentu
- Memeriksa nomor KTP duplikat

### 🏠 Manajemen Kamar (Pemilik)
- Melihat daftar semua kamar
- Menambah kamar baru
- Mengedit informasi kamar
- Menghapus kamar
- Mencari kamar berdasarkan keyword
- Melihat kamar yang tersedia (status kosong)
- Update status kamar (kosong/terisi)

### 💰 Manajemen Tagihan (Pemilik)
- Melihat semua tagihan beserta informasi penghuni
- Menambah tagihan baru untuk penghuni
- Mengedit informasi tagihan
- Menghapus tagihan
- Mencari tagihan berdasarkan keyword
- Konfirmasi pembayaran tagihan
- Filter tagihan yang belum lunas

### 👤 Menu Penghuni
- Melihat profil pribadi (nama, no KTP, kamar)
- Melihat daftar tagihan pribadi
- Mendaftar untuk mendapatkan kamar
- Keluar dari kamar (mengosongkan kamar)
- Logout dari sistem

### 🔄 Sistem Terintegrasi
- Update otomatis status kamar ketika penghuni masuk/keluar
- Validasi input untuk semua operasi CRUD
- Konfirmasi untuk operasi penting (hapus, keluar kamar, dll)
- Tampilan tabel yang terformat rapi untuk data

## 🗂️ Struktur Folder

- `src/` : Menyimpan seluruh file source code Java
  - `models/` : Class yang merepresentasikan entitas (Penghuni, Pemilik, Kamar, Tagihan, dsb.)
  - `controllers/` : Class yang menangani logika seperti login, register, CRUD, autentikasi
  - `database/` : Koneksi dan konfigurasi database (JDBC)
  - `DAO/` :  Data Access Object, berisi class untuk mengelola operasi database (insert, update, delete, select) untuk setiap model
  - `views/` : Tampilan berbasis CLI dan interaksi pengguna
  - `Main.java` : Entry point aplikasi
- `lib/` : Menyimpan dependency eksternal (jika diperlukan)
- `bin/` : Folder hasil kompilasi
- `README.md` : Dokumentasi proyek

## 🛠️ Teknologi yang Digunakan

- ☕ Java (JDK 17+)
- 🐬 MySQL
- 🔌 JDBC
- 🧑‍💻 VS Code dengan Extension Java Pack

## 👥 Anggota Kelompok

- 👤 Muhammad Naufal Dzaki Adani (23081010130)
- 👤 Muhammad Rafi Walidain (23081010005)
- 👤 Khansa Fawwazy Adna (23081010096)
- 👤 Muhammad Rizki Darmawan (23081010238)
- 👤 Akhmad Azrul Arsyadhany (23081010281)

## 👨‍🏫 Dosen Pengampu

**Ardhon Rakhmadi, S.Tr.T., M.Kom**

## 🏫 Kelas

**Pemrograman Berorientasi Objek H-081**

## 🧪 Panduan Instalasi & Eksekusi

1. ✅ Pastikan MySQL dan Java JDK telah terinstall
2. 🗄️ Buat database dan jalankan query SQL yang telah disediakan
3. 📁 Buka project di VS Code
4. ▶️ Jalankan program melalui `Main.java`

---

> 📌 *Proyek ini disusun untuk memenuhi tugas **Final Project Mata Kuliah Pemrograman Berorientasi Objek***  
