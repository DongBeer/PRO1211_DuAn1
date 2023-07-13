package dongnvph30597.fpoly.app_labtopstore.model;

public class ThuongHieu {
    public int maTH;
    public String tenTH;
    public int imgTH;

    public ThuongHieu() {
    }

    public ThuongHieu(int maTH, String tenTH, int imgTH) {
        this.maTH = maTH;
        this.tenTH = tenTH;
        this.imgTH = imgTH;
    }

    public int getMaTH() {
        return maTH;
    }

    public void setMaTH(int maTH) {
        this.maTH = maTH;
    }

    public String getTenTH() {
        return tenTH;
    }

    public void setTenTH(String tenTH) {
        this.tenTH = tenTH;
    }

    public int getImgTH() {
        return imgTH;
    }

    public void setImgTH(int imgTH) {
        this.imgTH = imgTH;
    }
}
