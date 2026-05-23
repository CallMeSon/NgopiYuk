# ☕ NgopiYuk - Boutique Coffee Shop Discovery Application

Selamat datang di **NgopiYuk**! 🌟 Sebuah aplikasi mobile berbasis Android yang dirancang khusus untuk mempermudah para penikmat kopi dan penjelajah perkotaan dalam mencari dan mengeksplorasi berbagai kedai kopi butik (*boutique coffee shops*) terbaik di sekitar mereka.

Aplikasi ini tidak hanya fungsional, tetapi juga dibangun dengan estetika **Modern-Organic** premium yang terinspirasi dari kehangatan, aroma, dan nuansa kedai kopi butik (menggunakan warna tanah, espresso, crema, dan matcha).

---

## 🎨 Sistem Desain (Design System)

Aplikasi ini diimplementasikan dengan mengikuti panduan ketat dari [DESIGN.MD](file:///c:/StudyClubs/finpro/DESIGN.MD). Berikut adalah detail elemen desain yang digunakan:

### 🔴 Palet Warna (Color Palette)
*   **Primary (Espresso - `#442A22`)**: Digunakan untuk aksi utama, branding, dan tajuk halaman tingkat tinggi. Menghadirkan rasa kedalaman dan kekuatan kopi espresso.
*   **Secondary (Crema - `#E1E1C9` / `#FAF9F6`)**: Warna dasar hangat untuk kartu, latar belakang modul, dan kontainer, memberikan rasa tenang (*calm/asik*).
*   **Accent (Matcha - `#08531E`)**: Digunakan secara eksklusif untuk indikator positif (seperti badge "Buka", "Eco-friendly", atau status sukses).
*   **Neutral (Charcoal - `#1B1C1C`)**: Digunakan untuk teks isi (*body text*) dan ikon agar keterbacaan tetap tinggi dan tajam.

### ✍️ Tipografi (Typography)
*   **Plus Jakarta Sans**: Digunakan untuk tajuk (*headlines* dan *titles*) guna memberikan kepribadian yang modern, ramah, dan geometris.
*   **Be Vietnam Pro**: Digunakan untuk teks isi (*body text*), deskripsi, dan teks fungsional lainnya demi keterbacaan optimal pada layar ponsel.

---

## 🚀 Fitur Utama (Key Features)

1.  **Dashboard & Eksplorasi Pintar (Smart Discovery)**
    *   Cari kedai kopi favorit secara instan menggunakan fitur pencarian dinamis.
    *   Saring kedai kopi berdasarkan label kategori gaya hidup, seperti **Modern**, **Traditional**, atau **Work-friendly** menggunakan sistem chip selektor interaktif.
2.  **Detail Kedai Kopi Interaktif (Comprehensive Details)**
    *   Tampilan jam operasional, alamat lengkap, dan jarak ke lokasi kedai kopi.
    *   Visualisasi daftar fasilitas lengkap (Wi-Fi, Power Outlet, Outdoor Seating, Pet Friendly, dsb.).
    *   Daftar menu andalan lengkap dengan harga dan rating.
3.  **Sistem Review & Ulasan Pengguna (User Reviews)**
    *   Membaca ulasan dari pelanggan lain.
    *   Menambahkan ulasan baru secara instan melalui modal ulasan interaktif beserta bintang rating dinamis.
    *   **Kalkulasi Rating Dinamis**: Mengintegrasikan rating bawaan dengan ulasan baru lokal dan memperbarui skor serta total ulasan secara instan pada header halaman detail kafe via `ReviewsManager`.
4.  **Daftar Favorit (Bookmarks/Favorites) & Konfirmasi Unbookmark**
    *   Simpan kedai kopi favorit Anda untuk diakses kembali dengan cepat lewat tab *Favorites* khusus.
    *   **Opsi B: MaterialAlertDialog Unbookmark**: Ketika pengguna melepas bookmark (unbookmark) di halaman detail, card list, maupun long-press bottom sheet, aplikasi akan memunculkan dialog konfirmasi Material3 sebelum menghapus data.
    *   **Dinamis Empty State**: Halaman *Favorites* secara otomatis mendeteksi dan melakukan refresh instan untuk memunculkan ilustrasi empty state jika seluruh kafe telah di-unbookmark.
5.  **Profil & Statistik Terbagi (Opsi A - Split Layout)**
    *   **TabLayout & ViewPager2**: Mengimplementasikan navigasi horizontal di halaman Profil yang membagi informasi menjadi dua tab setara:
        *   **Tab Info**: Statistik profil user (skor loyalty points, level keanggotaan, total ulasan, dsb.) beserta opsi menu bantuan.
        *   **Tab Vouchers**: Daftar vertikal yang scrollable menampilkan voucher promo aktif milik pengguna yang siap diklaim.

---

## 🛠️ Arsitektur & Struktur Proyek (Project Structure)

Proyek ini dibangun menggunakan arsitektur **Single Activity** berbasis **Android Jetpack Navigation Component** untuk transisi layar yang mulus dan penanganan navigasi tipe data yang aman menggunakan **Safe Args**.

Seluruh data statis, ulasan bawaan (*initial reviews*), dan menu andalan (*signature brews & pastries*) telah **dimigrasikan sepenuhnya ke dalam file database JSON terpadu** di folder `assets` guna menghilangkan *hardcoded boilerplate code* di Kotlin sumber.

Berikut adalah struktur folder utama dalam kode sumber Kotlin:

```text
app/src/main/java/com/android/ngopiyuk/
│
├── 📂 adapter          # Recycler View adapters (mis. CoffeeShopAdapter untuk kafe, VoucherAdapter untuk promo)
├── 📂 fragment         # Halaman/Layar Aplikasi (Dashboard, Detail, Pemesanan, Favorit, Profil, dsb.)
│   ├── AboutFragment.kt
│   ├── CoffeeDetailFragment.kt
│   ├── DashboardFragment.kt
│   ├── FavoritesFragment.kt
│   ├── PemesananFragment.kt
│   ├── ProfileFragment.kt
│   └── SettingsFragment.kt
│
├── 📂 model            # Kelas data/domain entity (CoffeeShop, MenuItem, Review)
├── 📂 ui               # Komponen UI kustom dan Dialog (CoffeeOptionsBottomSheet)
│
├── 📂 utils            # Kelas utilitas pendukung (FavoritesManager, ReviewsManager, JsonHelper)
│
├── MainActivity.kt     # Aktivitas utama sebagai kontainer NavHostFragment
└── SplashActivity.kt   # Layar pembuka aplikasi dengan micro-animation
```

Latar belakang data kedai kopi bersumber dari file JSON lokal yang disimpan di folder `assets`:
*   `app/src/main/assets/coffee_shops.json` (Daftar kafe lengkap beserta ulasan dinamis, fasilitas, menu hidangan, dll.)

---

## 💻 Teknologi yang Digunakan (Tech Stack)

*   **Bahasa Utama**: [Kotlin](https://kotlinlang.org/)
*   **Android SDK**: targetSdk 36, minSdk 32, compileSdk 36.
*   **User Interface**: XML Layouts terintegrasi dengan **Google Material Design Components 3 (M3)**.
*   **Navigation & Safe Args**: `androidx.navigation` untuk manajemen perpindahan halaman yang tangguh.
*   **Data Parsing**: [Gson](https://github.com/google/gson) untuk serialisasi dan deserialisasi data kedai kopi dari database JSON lokal.
*   **Manajemen Gambar**: Memuat drawable lokal secara dinamis menggunakan metode `getIdentifier` berbasis nama string.
*   **Penyimpanan Lokal**: `SharedPreferences` untuk menyimpan data bookmark/favorites dan ulasan lokal buatan pengguna secara persisten.

---

## ⚙️ Langkah Memulai & Menjalankan Aplikasi (Getting Started)

### Prasyarat (Requirements)
*   **Android Studio** (Disarankan versi Ladybug 2024.2.1 atau lebih baru).
*   **Java Development Kit (JDK)** versi 11 atau 17.
*   Perangkat Android fisik atau Emulator dengan API level minimum 32.

### Cara Membuka Proyek di Android Studio
1.  Buka **Android Studio**.
2.  Pilih **File** -> **Open...**
3.  Arahkan ke folder repositori proyek ini (`c:\StudyClubs\finpro`).
4.  Tunggu hingga proses sinkronisasi Gradle (*Gradle sync*) selesai secara otomatis.
5.  Hubungkan perangkat Android Anda atau jalankan emulator.
6.  Klik tombol **Run** (ikon segitiga hijau) di bagian toolbar Android Studio untuk memulai instalasi aplikasi **NgopiYuk** pada perangkat Anda.

---

## 📝 Panduan Melakukan Dokumentasi Kode

Untuk menjaga kualitas kode seiring berkembangnya aplikasi ini, sangat disarankan untuk melakukan dokumentasi kode dengan metode standar berikut:

### 1. Menulis Dokumentasi Kode Menggunakan KDoc
Setiap kali Anda membuat fungsi, kelas, atau variabel baru yang kompleks, tulislah komentar bertipe **KDoc** (format dokumentasi standar Kotlin). 

Contoh penulisan KDoc pada fungsi atau kelas:
```kotlin
/**
 * Mengambil daftar ulasan untuk kedai kopi tertentu berdasarkan ID kedai kopi.
 *
 * @param context Konteks aplikasi yang digunakan untuk mengakses berkas lokal.
 * @param shopId ID unik dari kedai kopi yang ingin dicari ulasannya.
 * @return Daftar objek [Review] yang sesuai dengan ID kedai kopi tersebut.
 */
fun getReviewsForShop(context: Context, shopId: Int): List<Review> {
    // Logika pemrosesan ulasan
}
```

### 2. Menggunakan Dokka untuk Pembuatan Halaman Dokumentasi Otomatis (HTML/Markdown)
**Dokka** adalah mesin dokumentasi resmi untuk Kotlin yang setara dengan Javadoc. Dokka dapat memproses komentar **KDoc** Anda dan menghasilkan situs web dokumentasi HTML yang interaktif dan sangat premium.

#### Langkah Integrasi Dokka ke Proyek Ini:
1.  Buka berkas `build.gradle.kts` tingkat proyek (Root) Anda, tambahkan plugin Dokka:
    ```kotlin
    plugins {
        // ... plugin lainnya
        id("org.jetbrains.dokka") version "1.9.20"
    }
    ```
2.  Buka berkas `app/build.gradle.kts`, lalu terapkan plugin Dokka di bagian atas:
    ```kotlin
    plugins {
        // ... plugin lainnya
        id("org.jetbrains.dokka")
    }
    ```
3.  Jalankan perintah berikut di Terminal Android Studio untuk mengekspor seluruh dokumentasi proyek menjadi halaman web HTML interaktif:
    ```bash
    ./gradlew dokkaHtml
    ```
4.  Hasil dokumentasi HTML premium akan tersimpan di dalam folder `app/build/dokka/html/`. Anda cukup membuka file `index.html` di browser Anda untuk melihat kamus kode visual yang luar biasa!

---

💡 **Butuh bantuan atau pengembangan lebih lanjut?**
Gunakan asisten AI untuk memandu Anda menambahkan fitur baru, memoles desain sesuai `DESIGN.MD`, atau menulis pengujian unit untuk menjamin keandalan aplikasi NgopiYuk! Selamat coding! ☕✨
