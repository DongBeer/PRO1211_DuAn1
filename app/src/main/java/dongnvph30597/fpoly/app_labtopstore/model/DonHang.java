package dongnvph30597.fpoly.app_labtopstore.model;

public class DonHang {
    public int maHD;
    public int maUser;
    public String maAdmin;
    public String ngay;
    public int tongTien;
    public int trangThai;
    public int trangThaiDG;
    public String ghiChu;

    public DonHang(int maHD, int maUser, String maAdmin, String ngay, int tongTien, int trangThai) {
        this.maHD = maHD;
        this.maUser = maUser;
        this.maAdmin = maAdmin;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public DonHang() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getMaAdmin() {
        return maAdmin;
    }

    public void setMaAdmin(String maAdmin) {
        this.maAdmin = maAdmin;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getTrangThaiDG() {
        return trangThaiDG;
    }

    public void setTrangThaiDG(int trangThaiDG) {
        this.trangThaiDG = trangThaiDG;
    }
}
