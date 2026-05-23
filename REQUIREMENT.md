  
**INSTRUKSI TUGAS 2 & FINAL PROJECT**  
**STUDY CLUB ANDROID BEGINNER**
     
4. **Deskripsi Tugas**  
1. **Materi untuk Tugas 2 (Hasil Sederhana)**

   Tugas 2 merupakan tahap awal dari Final Project, sehingga fokus utamanya adalah pada struktur dasar dan komponen UI inti;

* **Fragment & Lifecycle**: Gunakan Fragment untuk memisahkan bagian UI agar aplikasi lebih modular dan responsif. Anda perlu memahami *callback method* seperti ‘onViewCreated’ untuk melakukan inisialisasi komponen UI.  
* **Navigation Component (Dasar)**: Terapkan navigasi dasar antar Fragment menggunakan *Navigation Graph XML* agar struktur perpindahan layar sudah terencana sejak awal.  
* **Bottom Navigation**: Jika aplikasi Anda memiliki 2–5 menu utama, terapkan *Bottom Navigation* di bagian bawah layar untuk memberikan akses cepat bagi pengguna.  
* **Slicing UI & Design System Dasar**: Lakukan *slicing code* sederhana dengan memindahkan nilai-nilai warna ke ‘colors.xml’ dan ukuran ke ‘dimens.xml’ agar UI mulai terlihat seragam.


2. **Materi untuk Final Project (Versi Lengkap)**

   Untuk Final Project, Anda harus menyempurnakan aplikasi dengan fitur navigasi tingkat lanjut dan detail Material Design 3\. Materi tambahan yang perlu diterapkan meliputi:

* **Tab Layout**: Gunakan *Tab Layout* yang dikombinasikan dengan *ViewPager2* untuk navigasi horizontal antar konten yang setara.  
* **Option Menu**: Tambahkan menu di pojok kanan atas (ikon titik tiga) untuk aksi-aksi sekunder seperti "Settings" atau "About".  
* **Material Design 3 Components**:  
  * **TopAppBar**: Implementasikan ‘CenterAligned’ atau ‘Large’ TopAppBar sesuai kebutuhan halaman untuk navigasi back dan action icon.  
  * **Dialog & Snackbar**: Gunakan *MaterialAlertDialog* untuk konfirmasi aksi penting (seperti hapus data) dan *Snackbar* sebagai feedback singkat setelah pengguna melakukan aksi.  
  * **Bottom Sheet**: Terapkan *Modal Bottom Sheet* untuk menampilkan opsi tambahan tanpa harus berpindah halaman.  
* **Advanced Navigation**: Pastikan pengelolaan *Back Stack* berjalan otomatis lewat Navigation Component dan gunakan *Safe Args* jika perlu mengirim data antar fragment secara aman.  