/**
 * Kelas Minuman - subclass dari MenuItem.
 * Konsep: INHERITANCE (extends MenuItem)
 */
public class Minuman extends MenuItem {

    // Atribut tambahan khusus Minuman
    private String jenisMinuman; // contoh: "Dingin", "Panas", "Jus"

    // Constructor
    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman"); // panggil constructor MenuItem
        this.jenisMinuman = jenisMinuman;
    }

    // Getter & Setter
    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenisMinuman) { this.jenisMinuman = jenisMinuman; }

    /**
     * POLYMORPHISM: implementasi tampilMenu() khusus Minuman.
     */
    @Override
    public void tampilMenu() {
        System.out.printf("  [MINUMAN] %-22s | Rp %,10.0f | Jenis: %s%n",
                getNama(), getHarga(), jenisMinuman);
    }

    /**
     * Format simpan ke file: MINUMAN,nama,harga,jenisMinuman
     */
    @Override
    public String toFileString() {
        return "MINUMAN," + getNama() + "," + getHarga() + "," + jenisMinuman;
    }
}
