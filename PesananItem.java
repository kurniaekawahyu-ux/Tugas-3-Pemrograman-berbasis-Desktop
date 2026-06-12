/**
 * Kelas PesananItem - menyimpan satu item yang dipesan beserta jumlahnya.
 * Digunakan oleh kelas Pesanan untuk mencatat detail setiap item pesanan.
 * Konsep: ENCAPSULATION
 */
public class PesananItem {

    // ENCAPSULATION: atribut private
    private MenuItem menuItem;  // item menu yang dipesan
    private int jumlah;         // berapa porsi/gelas dipesan

    // Constructor
    public PesananItem(MenuItem menuItem, int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah pesanan harus lebih dari 0!");
        }
        this.menuItem = menuItem;
        this.jumlah = jumlah;
    }

    // Getter & Setter
    public MenuItem getMenuItem() { return menuItem; }

    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) {
        if (jumlah <= 0) throw new IllegalArgumentException("Jumlah harus lebih dari 0!");
        this.jumlah = jumlah;
    }

    /**
     * Subtotal = harga akhir item x jumlah pesanan.
     */
    public double getSubtotal() {
        return menuItem.getHargaAkhir() * jumlah;
    }

    /**
     * Format ringkas untuk ditampilkan di struk.
     * Contoh: Nasi Goreng          x2   Rp  50.000
     */
    public String toStringStruk() {
        return String.format("%-25s x%-3d Rp %,.0f",
                menuItem.getNama(), jumlah, getSubtotal());
    }

    /**
     * Tampilkan di layar saat melihat isi pesanan.
     */
    public void tampilkan() {
        System.out.printf("   %-25s x%-3d @ Rp %,.0f = Rp %,.0f%n",
                menuItem.getNama(), jumlah,
                menuItem.getHargaAkhir(), getSubtotal());
    }
}
