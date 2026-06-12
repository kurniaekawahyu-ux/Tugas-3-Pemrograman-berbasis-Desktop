
public class Diskon extends MenuItem {

    // Persentase diskon antara 0.0 - 1.0 (contoh: 0.20 = diskon 20%)
    private double diskon;

    // Constructor
    public Diskon(String nama, double hargaAsli, String kategori, double diskon) {
        super(nama, hargaAsli, kategori); // panggil constructor MenuItem
        if (diskon < 0 || diskon > 1) {
            throw new IllegalArgumentException("Diskon harus antara 0.0 dan 1.0!");
        }
        this.diskon = diskon;
    }

    // Getter & Setter
    public double getDiskon() { return diskon; }
    public void setDiskon(double diskon) {
        if (diskon < 0 || diskon > 1) {
            throw new IllegalArgumentException("Diskon harus antara 0.0 dan 1.0!");
        }
        this.diskon = diskon;
    }

    /**
     * Override: harga akhir sudah dipotong diskon.
     */
    @Override
    public double getHargaAkhir() {
        return getHarga() * (1 - diskon);
    }

    /**
     * POLYMORPHISM: implementasi tampilMenu() khusus Diskon.
     */
    @Override
    public void tampilMenu() {
        System.out.printf("  [DISKON]  %-22s | Rp %,10.0f -> Rp %,10.0f | Diskon: %.0f%% | Kat: %s%n",
                getNama(), getHarga(), getHargaAkhir(), diskon * 100, getKategori());
    }

    /**
     * Format simpan ke file: DISKON,nama,harga,kategori,diskon
     */
    @Override
    public String toFileString() {
        return "DISKON," + getNama() + "," + getHarga() + "," + getKategori() + "," + diskon;
    }

    @Override
    public String toStringStruk() {
        return String.format("%-25s Rp %,.0f (diskon %.0f%%)",
                getNama(), getHargaAkhir(), diskon * 100);
    }
}
