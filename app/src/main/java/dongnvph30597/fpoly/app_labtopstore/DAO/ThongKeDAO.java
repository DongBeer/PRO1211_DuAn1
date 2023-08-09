package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.TopSP;

public class ThongKeDAO {
    private SQLiteDatabase db;

    public ThongKeDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        this.db = db_store.getWritableDatabase();
    }

    public int getTotalProduct() {
        int total = 0;

        String query = "SELECT COUNT(*) FROM SanPham where trangThaiSP = 0 or trangThaiSP = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;

    }

    public int getTotalProductAdmin() {
        int total = 0;

        String query = "SELECT COUNT(*) FROM SanPham";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;

    }


    @SuppressLint("Range")
    public ArrayList<TopSP> Top5SPbanchay(){
        String query = "SELECT SanPham.maSP, tenSP, imgSP, SUM(HoaDonChiTiet.soLuong) AS totalSold " +
                "FROM SanPham " +
                "JOIN HoaDonChiTiet ON SanPham.maSP = HoaDonChiTiet.maSP " +
                "GROUP BY SanPham.maSP, tenSP " +
                "ORDER BY totalSold DESC " +
                "LIMIT 5";

        Cursor cursor = db.rawQuery(query, null);
        ArrayList<TopSP> arr = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String imgSP = cursor.getString(cursor.getColumnIndex("imgSP"));
                 int maSP = cursor.getInt(cursor.getColumnIndex("maSP"));
                String tenSP = cursor.getString(cursor.getColumnIndex("tenSP"));
                int totalSold = cursor.getInt(cursor.getColumnIndex("totalSold"));

                // Tạo đối tượng SanPham từ dữ liệu truy vấn và thêm vào danh sách
                TopSP topSP = new TopSP();
                topSP.setImgSP(imgSP);
                topSP.setMaSP(maSP);
                topSP.setTenSP(tenSP);
                topSP.setSoLuongBan(totalSold);
                arr.add(topSP);

            } while (cursor.moveToNext());
        }
        // Đừng quên đóng con trỏ sau khi sử dụng xong
        if (cursor != null) {
            cursor.close();
        }

        return arr;
    }



    @SuppressLint("Range")
    public int getThongke(String startDate, String endDate) {
        int totalRevenue = 0;

        String sql = "SELECT SUM(tongTien) FROM HoaDon WHERE trangThai = 3 and ngay BETWEEN ? AND ?";
        String[] selectionArgs = new String[]{startDate, endDate};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            totalRevenue = cursor.getInt(0);
        }

        cursor.close();
        return totalRevenue;
    }


    @SuppressLint("Range")
    public int getDoanhThuT8() {
        int tongDoanhThu = 0;

        String query = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon WHERE trangThai = 3 " +
                "AND ngay >= '2023/08/01' AND ngay <= '2023/08/31'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            tongDoanhThu = cursor.getInt(cursor.getColumnIndex("tongDoanhThu"));
        }

        cursor.close();

        return tongDoanhThu;
    }

    @SuppressLint("Range")
    public int getDoanhThuT9() {
        int tongDoanhThu = 0;

        String query = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon WHERE trangThai = 3 " +
                "AND ngay >= '2023/09/01' AND ngay <= '2023/09/30'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            tongDoanhThu = cursor.getInt(cursor.getColumnIndex("tongDoanhThu"));
        }

        cursor.close();

        return tongDoanhThu;
    }

    @SuppressLint("Range")
    public int getDoanhThuT10() {
        int tongDoanhThu = 0;

        String query = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon WHERE trangThai = 3 " +
                "AND ngay >= '2023/10/01' AND ngay <= '2023/10/31'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            tongDoanhThu = cursor.getInt(cursor.getColumnIndex("tongDoanhThu"));
        }

        cursor.close();

        return tongDoanhThu;
    }

    @SuppressLint("Range")
    public int getDoanhThuT11() {
        int tongDoanhThu = 0;

        String query = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon WHERE trangThai = 3 " +
                "AND ngay >= '2023/11/01' AND ngay <= '2023/11/30'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            tongDoanhThu = cursor.getInt(cursor.getColumnIndex("tongDoanhThu"));
        }

        cursor.close();

        return tongDoanhThu;
    }

    @SuppressLint("Range")
    public int getDoanhThuT12() {
        int tongDoanhThu = 0;

        String query = "SELECT SUM(tongTien) AS tongDoanhThu FROM HoaDon WHERE trangThai = 3 " +
                "AND ngay >= '2023/12/01' AND ngay <= '2023/12/31'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            tongDoanhThu = cursor.getInt(cursor.getColumnIndex("tongDoanhThu"));
        }

        cursor.close();

        return tongDoanhThu;
    }




}