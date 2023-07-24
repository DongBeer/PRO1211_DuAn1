package dongnvph30597.fpoly.app_labtopstore.model;

public class GioHang {
    public int maGH;
    public int maUser;
    public int maSP;
    public int soLuong;
    public int giaSP;
    public String tenSP;
    public String imgSP;
    public int trangThai;


    public GioHang(int maGH, int maUser, int maSP, int soLuong, int giaSP, String tenSP, String imgSP) {
        this.maGH = maGH;
        this.maUser = maUser;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
        this.tenSP = tenSP;
        this.imgSP = imgSP;
    }

    public GioHang(int maGH, int maUser, int maSP, int soLuong, int giaSP) {
        this.maGH = maGH;
        this.maUser = maUser;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaSP = giaSP;
    }

    public GioHang() {
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaGH() {
        return maGH;
    }

    public void setMaGH(int maGH) {
        this.maGH = maGH;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getImgSP() {
        return imgSP;
    }

    public void setImgSP(String imgSP) {
        this.imgSP = imgSP;
    }
}
