import java.util.ArrayList;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Kelas Pesanan - mencatat pesanan satu pelanggan.
 * Menggunakan ArrayList<PesananItem> untuk menyimpan item + jumlahnya.
 * Konsep: ENCAPSULATION, OPERASI FILE, EXCEPTION HANDLING
 */
public class Pesanan {

    // ENCAPSULATION: semua atribut private
    private String namaPelanggan;
    private ArrayList<PesananItem> daftarPesanan; // pakai PesananItem
    private LocalDateTime waktuPesan;
    private static final String FILE_STRUK = "struk_pesanan.txt";

    // Constructor
    public Pesanan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
        this.daftarPesanan = new ArrayList<>();
        this.waktuPesan = LocalDateTime.now();
    }

    // ---- Tambah Item ke Pesanan ----
    public void tambahItem(MenuItem item, int jumlah) {
        // Cek apakah item sudah ada, jika ada tambah jumlahnya saja
        for (PesananItem pi : daftarPesanan) {
            if (pi.getMenuItem().getNama().equals(item.getNama())) {
                pi.setJumlah(pi.getJumlah() + jumlah);
                System.out.println("  + '" + item.getNama() + "' jumlah diperbarui menjadi " + pi.getJumlah() + ".");
                return;
            }
        }
        // Belum ada, tambah PesananItem baru
        daftarPesanan.add(new PesananItem(item, jumlah));
        System.out.println("  + '" + item.getNama() + "' x" + jumlah + " ditambahkan ke pesanan.");
    }

    // ---- Hapus Item dari Pesanan ----
    public void hapusItem(int nomor) {
        // EXCEPTION HANDLING
        if (nomor < 1 || nomor > daftarPesanan.size()) {
            throw new IndexOutOfBoundsException("Nomor pesanan tidak valid!");
        }
        String nama = daftarPesanan.get(nomor - 1).getMenuItem().getNama();
        daftarPesanan.remove(nomor - 1);
        System.out.println("  - '" + nama + "' dihapus dari pesanan.");
    }

    // ---- Hitung Total Biaya ----
    public double hitungTotal() {
        double total = 0;
        for (PesananItem pi : daftarPesanan) {
            total += pi.getSubtotal(); // subtotal sudah otomatis kena diskon
        }
        return total;
    }

    // ---- Tampilkan Isi Pesanan ----
    public void tampilkanPesanan() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("  (Belum ada item yang dipesan.)");
            return;
        }
        System.out.println("  " + "-".repeat(55));
        for (int i = 0; i < daftarPesanan.size(); i++) {
            System.out.printf("  %d. ", i + 1);
            daftarPesanan.get(i).tampilkan();
        }
        System.out.printf("  %-30s Total: Rp %,.0f%n", "", hitungTotal());
        System.out.println("  " + "-".repeat(55));
    }

    // ---- Cetak Struk ke Layar ----
    public void cetakStruk() {
        System.out.println(buatStruk());
    }

    // ---- Buat Teks Struk ----
    private String buatStruk() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(52)).append("\n");
        sb.append("        RESTORAN NUSANTARA JAYA\n");
        sb.append("=".repeat(52)).append("\n");
        sb.append(String.format("Pelanggan : %s%n", namaPelanggan));
        sb.append(String.format("Waktu     : %s%n", waktuPesan.format(fmt)));
        sb.append("-".repeat(52)).append("\n");
        sb.append(String.format("%-29s %s%n", "Item (Qty)", "Subtotal"));
        sb.append("-".repeat(52)).append("\n");

        for (PesananItem pi : daftarPesanan) {
            sb.append(pi.toStringStruk()).append("\n");
        }

        sb.append("-".repeat(52)).append("\n");
        sb.append(String.format("%-32s Rp %,.0f%n", "TOTAL PEMBAYARAN", hitungTotal()));
        sb.append("=".repeat(52)).append("\n");
        sb.append("     Terima kasih telah berkunjung!\n");
        sb.append("=".repeat(52)).append("\n");
        return sb.toString();
    }

    // ----------------------------------------------------------------
    // OPERASI FILE: Simpan struk ke file (append, tidak menimpa)
    // ----------------------------------------------------------------
    public void simpanStrukKeFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_STRUK, true))) { // true = append
            writer.write(buatStruk());
            writer.newLine();
            System.out.println("  ✔ Struk berhasil disimpan ke '" + FILE_STRUK + "'.");
        } catch (IOException e) {
            System.out.println("  ✘ Gagal menyimpan struk: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // OPERASI FILE: Muat dan tampilkan semua riwayat struk
    // ----------------------------------------------------------------
    public static void tampilkanSemuaStruk() {
        File file = new File(FILE_STRUK);
        if (!file.exists()) {
            System.out.println("  (Belum ada riwayat struk tersimpan.)");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_STRUK))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("  ✘ Gagal membaca file struk: " + e.getMessage());
        }
    }

    public boolean isEmpty() { return daftarPesanan.isEmpty(); }
    public String getNamaPelanggan() { return namaPelanggan; }
    public ArrayList<PesananItem> getDaftarPesanan() { return daftarPesanan; }
}
