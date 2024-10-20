# My Diary App
***My Diary App*** adalah aplikasi Android yang memungkinkan pengguna untuk mengelola catatan harian pribadi dengan fitur-fitur seperti manajemen pengguna, pembuatan catatan harian, pengurutan, dan pencarian

## FITUR
1. Autentikasi Pengguna (Login/Logout)
   
   Pengguna dapat mendaftar, masuk, dan keluar secara aman.

3. CRU Pengguna (Create, Read, Update)

   Pengguna dapat membuat akun, melihat profil, dan memperbarui informasi mereka.

4. CRUD Diary (Create, Read, Update, Delete)

   Fitur lengkap untuk mengelola catatan harian, termasuk membuat, mengedit, dan menghapus catatan.

5. Pengurutan (Sorting)
   
   Pengguna dapat mengurutkan catatan harian berdasarkan:

      Judul (Ascending/Descending)

      Tanggal (Ascending/Descending)

      Pencarian (Search)

  . Pengguna dapat mencari catatan harian berdasarkan:
      . Judul
      . Tanggal pembuatan

## Struktur Aplikasi
1. Splashscreen Activity
   . Layar splash pertama saat aplikasi dibuka.

2. Welcome Activity

3. Aktivitas onboarding dengan 4 fragment:
  . Fragment OneBoarding
  . Fragment TwoBoarding
  . Fragment ThreeBoarding
  . Fragment FourBoarding

3. Authentication Activity
  . Aktivitas otentikasi pengguna:
    . Login Fragment
    . Register Fragment

4. Dashboard Activity (dengan bottom navigation manual)
  . Navigasi utama dalam aplikasi dengan beberapa fragment:
    . Home Fragment
    . Mood Fragment
    . Add Diary Fragment
    . Diary List Fragment
    . Detail Diary Fragment (terhubung dengan Diary List)
    . Setting Fragment
   
5. Add Update Activity
    . Aktivitas untuk menambah atau memperbarui catatan harian.

6. Personal Setting Activity
  . Aktivitas untuk pengaturan personal pengguna.

## Arsitektur
Proyek ini menggunakan Clean Architecture dengan Dependency Injection (Hilt). Komponen arsitektur ini terdiri dari:

. Domain Layer: Berisi UseCases yang mengatur logika bisnis utama.
. Data Layer: Menggunakan Repository untuk mengelola data, dengan implementasi local storage menggunakan Room.
. Presentation Layer: Menggunakan ViewModel untuk mengelola data yang ditampilkan di UI, serta LiveData dan StateFlow untuk memperbarui UI secara reaktif.

## Teknologi yang Digunakan:
  . Room database untuk penyimpanan data lokal
  . DataStore untuk menyimpan preferensi (misalnya pengaturan sorting pengguna)
  . Kotlin Coroutines untuk operasi asinkron
  . Hilt untuk Dependency Injection
