/**
 * Kelas Makanan - subclass dari MenuItem.
 * Konsep: INHERITANCE (extends MenuItem)
 */
public class Makanan extends MenuItem {

    // Atribut tambahan khusus Makanan
    private String jenisMakanan; // contoh: "Makanan Berat", "Camilan", "Dessert"

    // Constructor
    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan"); // panggil constructor MenuItem
        this.jenisMakanan = jenisMakanan;
    }

    // Getter & Setter
    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenisMakanan) { this.jenisMakanan = jenisMakanan; }

    /**
     * POLYMORPHISM: implementasi tampilMenu() khusus Makanan.
     */
    @Override
    public void tampilMenu() {
        System.out.printf("  [MAKANAN] %-22s | Rp %,10.0f | Jenis: %s%n",
                getNama(), getHarga(), jenisMakanan);
    }

    /**
     * Format simpan ke file: MAKANAN,nama,harga,jenisMakanan
     */
    @Override
    public String toFileString() {
        return "MAKANAN," + getNama() + "," + getHarga() + "," + jenisMakanan;
    }
}
