package dongnvph30597.fpoly.app_labtopstore.model;

public class User {
    public int maUser;
    public String hoTen;
    public String tenDangnhap;
    public String matkhau;
    public String soDT;
    public String diaChi;
    public String imgUser;

    public static final String COL_PHOTO = "imgUser";

    public User(int maUser, String hoTen, String tenDangnhap, String matkhau, String soDT, String diaChi) {
        this.maUser = maUser;
        this.hoTen = hoTen;
        this.tenDangnhap = tenDangnhap;
        this.matkhau = matkhau;
        this.soDT = soDT;
        this.diaChi = diaChi;
    }

    public User(int maUser, String hoTen, String tenDangnhap, String matkhau, String soDT, String diaChi, String imgUser) {
        this.maUser = maUser;
        this.hoTen = hoTen;
        this.tenDangnhap = tenDangnhap;
        this.matkhau = matkhau;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.imgUser = imgUser;
    }

    public User() {
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenDangnhap() {
        return tenDangnhap;
    }

    public void setTenDangnhap(String tenDangnhap) {
        this.tenDangnhap = tenDangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "maUser=" + maUser +
                ", hoTen='" + hoTen + '\'' +
                ", tenDangnhap='" + tenDangnhap + '\'' +
                ", matkhau='" + matkhau + '\'' +
                ", soDT='" + soDT + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", imgUser='" + imgUser + '\'' +
                '}';
    }
}
