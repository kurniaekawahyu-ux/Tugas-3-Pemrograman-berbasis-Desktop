import java.util.Scanner;

/**
 * ============================================================
 *  Main.java - Program Utama Manajemen Restoran
 *  Tugas Praktik 3 - Pemrograman Berbasis Objek
 *
 *  Konsep yang diimplementasikan:
 *  - Abstraksi    : abstract class MenuItem
 *  - Inheritance  : Makanan, Minuman, Diskon extends MenuItem
 *  - Encapsulation: semua atribut private + getter/setter
 *  - Polymorphism : tampilMenu() dipanggil lewat tipe MenuItem
 *  - Exception    : try-catch untuk input & akses data
 *  - I/O & File   : simpan/muat menu & struk dari file .txt
 * ============================================================
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║    SELAMAT DATANG DI RESTORAN            ║");
        System.out.println("║         NUSANTARA JAYA  🍽               ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // Muat menu dari file saat program pertama dijalankan
        System.out.println("\n📂 Memuat data menu dari file...");
        menu.muatDariFile();

        boolean jalan = true;
        while (jalan) {
            tampilMenuUtama();
            int pilihan = bacaInteger("➤ Masukkan pilihan: ");

            switch (pilihan) {
                case 1:
                    menuTambahItem();
                    break;
                case 2:
                    System.out.println("\n📋 DAFTAR MENU RESTORAN:");
                    menu.tampilkanMenu();
                    break;
                case 3:
                    menuBuatPesanan();
                    break;
                case 4:
                    System.out.println("\n📜 RIWAYAT STRUK PESANAN:");
                    Pesanan.tampilkanSemuaStruk();
                    break;
                case 5:
                    System.out.println("\n💾 Menyimpan menu ke file...");
                    menu.simpanKeFile();
                    break;
                case 6:
                    System.out.println("\n💾 Menyimpan menu sebelum keluar...");
                    menu.simpanKeFile();
                    System.out.println("\n👋 Terima kasih! Sampai jumpa lagi!");
                    jalan = false;
                    break;
                default:
                    System.out.println("  ⚠ Pilihan tidak valid, silakan coba lagi.");
            }
        }
        scanner.close();
    }

    // ==============================================================
    // TAMPILKAN MENU UTAMA
    // ==============================================================
    private static void tampilMenuUtama() {
        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│               MENU UTAMA                 │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1. Tambah Item Menu Baru                │");
        System.out.println("│  2. Tampilkan Menu Restoran              │");
        System.out.println("│  3. Buat Pesanan Pelanggan               │");
        System.out.println("│  4. Lihat Riwayat Struk Pesanan          │");
        System.out.println("│  5. Simpan Menu ke File                  │");
        System.out.println("│  6. Keluar                               │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    // ==============================================================
    // SUB-MENU: TAMBAH ITEM BARU
    // ==============================================================
    private static void menuTambahItem() {
        System.out.println("\n── TAMBAH ITEM BARU ──");
        System.out.println("  1. Makanan");
        System.out.println("  2. Minuman");
        System.out.println("  3. Item dengan Diskon");
        int pilihan = bacaInteger("  Pilih jenis (1/2/3): ");

        System.out.print("  Nama item       : ");
        String nama = scanner.nextLine().trim();
        double harga = bacaDouble("  Harga (Rp)      : ");

        try {
            switch (pilihan) {
                case 1:
                    System.out.print("  Jenis makanan   : ");
                    String jenisMakanan = scanner.nextLine().trim();
                    menu.tambahItem(new Makanan(nama, harga, jenisMakanan));
                    break;
                case 2:
                    System.out.print("  Jenis minuman   : ");
                    String jenisMinuman = scanner.nextLine().trim();
                    menu.tambahItem(new Minuman(nama, harga, jenisMinuman));
                    break;
                case 3:
                    System.out.print("  Kategori item   : ");
                    String kategori = scanner.nextLine().trim();
                    double persen = bacaDouble("  Diskon (contoh 20 untuk 20%): ");
                    // EXCEPTION: validasi diskon dilakukan di constructor Diskon
                    menu.tambahItem(new Diskon(nama, harga, kategori, persen / 100.0));
                    break;
                default:
                    System.out.println("  ⚠ Pilihan tidak valid.");
            }
        } catch (IllegalArgumentException e) {
            // EXCEPTION HANDLING: tangkap error dari constructor
            System.out.println("  ✘ Input tidak valid: " + e.getMessage());
        }
    }

    // ==============================================================
    // SUB-MENU: BUAT PESANAN PELANGGAN
    // ==============================================================
    private static void menuBuatPesanan() {
        if (menu.getJumlahItem() == 0) {
            System.out.println("  ⚠ Menu masih kosong! Tambah item terlebih dahulu.");
            return;
        }

        System.out.print("\n  Nama pelanggan: ");
        String namaPelanggan = scanner.nextLine().trim();
        Pesanan pesanan = new Pesanan(namaPelanggan);

        boolean pesan = true;
        while (pesan) {
            System.out.println("\n══ PESANAN: " + namaPelanggan + " ══");
            System.out.println("  Isi pesanan saat ini:");
            pesanan.tampilkanPesanan();

            System.out.println("\n  Daftar Menu:");
            menu.tampilkanMenu();

            System.out.println("\n  [a] Tambah item");
            System.out.println("  [b] Hapus item dari pesanan");
            System.out.println("  [c] Selesai & cetak struk");
            System.out.print("  Pilihan [a/b/c]: ");
            String aksi = scanner.nextLine().trim().toLowerCase();

            switch (aksi) {
                case "a":
                    int nomorItem = bacaInteger("  Nomor item yang dipesan: ");
                    int jumlah = bacaInteger("  Jumlah (porsi/gelas)   : ");
                    try {
                        MenuItem itemDipilih = menu.getItem(nomorItem);
                        pesanan.tambahItem(itemDipilih, jumlah);
                    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
                        // EXCEPTION HANDLING
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;

                case "b":
                    if (pesanan.isEmpty()) {
                        System.out.println("  ⚠ Pesanan masih kosong.");
                    } else {
                        int nomorHapus = bacaInteger("  Nomor item yang dihapus: ");
                        try {
                            pesanan.hapusItem(nomorHapus);
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("  ✘ " + e.getMessage());
                        }
                    }
                    break;

                case "c":
                    if (pesanan.isEmpty()) {
                        System.out.println("  ⚠ Pesanan kosong. Kembali ke menu utama.");
                    } else {
                        System.out.printf("\n  💰 Total Biaya: Rp %,.0f%n", pesanan.hitungTotal());
                        pesanan.cetakStruk();
                        pesanan.simpanStrukKeFile();
                    }
                    pesan = false;
                    break;

                default:
                    System.out.println("  ⚠ Pilihan tidak dikenal, masukkan a, b, atau c.");
            }
        }
    }

    // ==============================================================
    // HELPER: Baca Integer (dengan validasi & exception handling)
    // ==============================================================
    private static int bacaInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Masukkan angka bulat yang valid!");
            }
        }
    }

    // ==============================================================
    // HELPER: Baca Double (dengan validasi & exception handling)
    // ==============================================================
    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double nilai = Double.parseDouble(scanner.nextLine().trim());
                if (nilai < 0) throw new NumberFormatException("Tidak boleh negatif");
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Masukkan angka yang valid (tidak boleh negatif)!");
            }
        }
    }
}
