package dongnvph30597.fpoly.app_labtopstore.model;

public class SanPham {
    public int maSP;
    public String tenSP;
    public String moTa;
    public int maTH;
    public int giaSP;
    public String loaiSP;
    public int soLuong;
    public String imgSP;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, String moTa, int maTH, int giaSP, String loaiSP, int soLuong, String imgSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.maTH = maTH;
        this.giaSP = giaSP;
        this.loaiSP = loaiSP;
        this.soLuong = soLuong;
        this.imgSP = imgSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaTH() {
        return maTH;
    }

    public void setMaTH(int maTH) {
        this.maTH = maTH;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getImgSP() {
        return imgSP;
    }

    public void setImgSP(String imgSP) {
        this.imgSP = imgSP;
    }
}
