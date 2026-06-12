import java.util.ArrayList;
import java.io.*;

/**
 * Kelas Menu - mengelola semua item menu restoran.
 * Mendukung operasi CRUD pada daftar menu dan operasi File I/O.
 * Konsep: ENCAPSULATION, OPERASI FILE
 */
public class Menu {

    // ENCAPSULATION: ArrayList disembunyikan, diakses via metode publik
    private ArrayList<MenuItem> daftarMenu;
    private static final String FILE_MENU = "menu.txt";

    // Constructor
    public Menu() {
        daftarMenu = new ArrayList<>();
    }

    // ---- Tambah Item ke Daftar Menu ----
    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
        System.out.println("  ✔ Item '" + item.getNama() + "' berhasil ditambahkan ke menu.");
    }

    // ---- Tampilkan Semua Menu (POLYMORPHISM terjadi di sini) ----
    public void tampilkanMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("  (Menu masih kosong. Silakan tambah item terlebih dahulu.)");
            return;
        }
        System.out.println("\n  " + "=".repeat(72));
        System.out.printf("  %-4s %-10s %-22s %-12s %-10s %s%n",
                "No.", "Tipe", "Nama", "Kategori", "Harga", "Keterangan");
        System.out.println("  " + "=".repeat(72));
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.printf("  %-4d", i + 1);
            daftarMenu.get(i).tampilMenu(); // POLYMORPHISM: panggil versi masing-masing subclass
        }
        System.out.println("  " + "=".repeat(72));
    }

    /**
     * Ambil item berdasarkan nomor urut (1-based).
     * EXCEPTION HANDLING: lempar exception jika nomor tidak valid.
     */
    public MenuItem getItem(int nomor) {
        if (nomor < 1 || nomor > daftarMenu.size()) {
            throw new IndexOutOfBoundsException(
                "Nomor item tidak valid! Masukkan angka 1 sampai " + daftarMenu.size() + ".");
        }
        return daftarMenu.get(nomor - 1);
    }

    public int getJumlahItem() { return daftarMenu.size(); }
    public ArrayList<MenuItem> getDaftarMenu() { return daftarMenu; }

    // ----------------------------------------------------------------
    // OPERASI FILE: Simpan semua menu ke file teks
    // ----------------------------------------------------------------
    public void simpanKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_MENU))) {
            for (MenuItem item : daftarMenu) {
                writer.write(item.toFileString()); // format CSV tiap item
                writer.newLine();
            }
            System.out.println("  ✔ Menu berhasil disimpan ke '" + FILE_MENU + "'.");
        } catch (IOException e) {
            System.out.println("  ✘ Gagal menyimpan menu: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // OPERASI FILE: Muat menu dari file teks
    // ----------------------------------------------------------------
    public void muatDariFile() {
        File file = new File(FILE_MENU);
        if (!file.exists()) {
            System.out.println("  (File '" + FILE_MENU + "' belum ada. Mulai dengan menu kosong.)");
            return;
        }
        daftarMenu.clear();
        int jumlah = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_MENU))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                baris = baris.trim();
                if (baris.isEmpty()) continue;

                String[] bagian = baris.split(",");
                switch (bagian[0].toUpperCase()) {
                    case "MAKANAN":
                        daftarMenu.add(new Makanan(
                            bagian[1],
                            Double.parseDouble(bagian[2]),
                            bagian[3]));
                        break;
                    case "MINUMAN":
                        daftarMenu.add(new Minuman(
                            bagian[1],
                            Double.parseDouble(bagian[2]),
                            bagian[3]));
                        break;
                    case "DISKON":
                        daftarMenu.add(new Diskon(
                            bagian[1],
                            Double.parseDouble(bagian[2]),
                            bagian[3],
                            Double.parseDouble(bagian[4])));
                        break;
                    default:
                        System.out.println("  (Tipe tidak dikenal: " + bagian[0] + ", dilewati)");
                }
                jumlah++;
            }
            System.out.println("  ✔ " + jumlah + " item menu dimuat dari '" + FILE_MENU + "'.");
        } catch (IOException e) {
            System.out.println("  ✘ Gagal memuat menu: " + e.getMessage());
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("  ✘ Format file menu tidak valid: " + e.getMessage());
        }
    }
}
