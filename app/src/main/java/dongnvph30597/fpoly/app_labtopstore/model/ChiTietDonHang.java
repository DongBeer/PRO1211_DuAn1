package dongnvph30597.fpoly.app_labtopstore.model;

public class ChiTietDonHang {
    public int maDHCT;
    public int maDonHang;
    public int maSanPham;
    public int soLuong;

    public ChiTietDonHang(int maDHCT, int maDonHang, int maSanPham, int soLuong) {
        this.maDHCT = maDHCT;
        this.maDonHang = maDonHang;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
    }

    public ChiTietDonHang() {
    }

    public int getMaDHCT() {
        return maDHCT;
    }

    public void setMaDHCT(int maDHCT) {
        this.maDHCT = maDHCT;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

}
