package dongnvph30597.fpoly.app_labtopstore.model;

public class Admin {
    public String maAdmin;
    public String hoTen;
    public String mkAdmin;

    public Admin() {
    }

    public Admin(String maAdmin, String hoTen, String mkAdmin) {
        this.maAdmin = maAdmin;
        this.hoTen = hoTen;
        this.mkAdmin = mkAdmin;
    }

    public String getMaAdmin() {
        return maAdmin;
    }

    public void setMaAdmin(String maAdmin) {
        this.maAdmin = maAdmin;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMkAdmin() {
        return mkAdmin;
    }

    public void setMkAdmin(String mkAdmin) {
        this.mkAdmin = mkAdmin;
    }
}
