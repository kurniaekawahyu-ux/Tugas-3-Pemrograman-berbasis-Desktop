/**
 * Kelas ABSTRAK MenuItem - kelas dasar semua item menu.
 * Konsep: ABSTRAKSI & ENCAPSULATION
 */
public abstract class MenuItem {

    // ENCAPSULATION: atribut private, akses via getter/setter
    private String nama;
    private double harga;
    private String kategori;

    // Constructor
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // ---- Getter & Setter ----
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) {
        if (harga < 0) throw new IllegalArgumentException("Harga tidak boleh negatif!");
        this.harga = harga;
    }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    /**
     * Metode ABSTRAK - wajib di-override oleh subclass.
     * Konsep: ABSTRAKSI + POLYMORPHISM
     */
    public abstract void tampilMenu();

    /**
     * Harga akhir (bisa di-override oleh Diskon).
     */
    public double getHargaAkhir() {
        return harga;
    }

    /**
     * Format teks untuk disimpan ke file (CSV).
     */
    public abstract String toFileString();

    /**
     * Format ringkas untuk struk pesanan.
     */
    public String toStringStruk() {
        return String.format("%-25s Rp %,.0f", nama, getHargaAkhir());
    }
}
