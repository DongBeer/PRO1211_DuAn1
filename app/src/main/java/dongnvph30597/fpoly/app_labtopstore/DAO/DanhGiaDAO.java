package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.DanhGia;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class DanhGiaDAO {
    private SQLiteDatabase db;

    public DanhGiaDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        this.db = db_store.getWritableDatabase();
    }

    public long insert(DanhGia danhGia) {
        ContentValues values = new ContentValues();
        values.put("maUser", danhGia.getMaUser());
        values.put("maSP", danhGia.getMaSP());
        values.put("danhGia", danhGia.getDangGia());
        values.put("nhanXet", danhGia.getNhanXet());
        return db.insert("DanhGia", null, values);
    }

    public ArrayList<DanhGia> getDanhGiaBymaSP(int maSP) {
        String sqlGH = "SELECT * FROM DanhGia WHERE maSP = ?";
        Cursor cursor = db.rawQuery(sqlGH, new String[]{String.valueOf(maSP)});
        ArrayList<DanhGia> arr = new ArrayList<>();
        HashSet<Integer> uniqueUserIds = new HashSet<>(); // Dùng để kiểm tra mã User đã xuất hiện

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int maUser = cursor.getInt(1);
                if (!uniqueUserIds.contains(maUser)) {
                    DanhGia danhGia = new DanhGia();
                    danhGia.setMaDG(cursor.getInt(0));
                    danhGia.setMaUser(maUser);
                    danhGia.setMaSP(cursor.getInt(2));
                    danhGia.setDangGia(cursor.getInt(3));
                    danhGia.setNhanXet(cursor.getString(4));
                    arr.add(danhGia);
                    uniqueUserIds.add(maUser); // Thêm mã User vào HashSet để đánh dấu đã xuất hiện
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }

    public float getAverageDanhGiaByMaSP(int maSP) {
        String sql = "SELECT AVG(danhGia) FROM DanhGia WHERE maSP = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maSP)});
        float averageDanhGia = 0;

        if (cursor.moveToFirst()) {
            averageDanhGia = cursor.getFloat(0);
        }

        cursor.close();
        return averageDanhGia;
    }

    public int getTongSoLuongDaBan(int maSP) {
        String sql = "SELECT SUM(soLuong) FROM HoaDonChiTiet WHERE maSP = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maSP)});
        int tongSoLuongDaBan = 0;

        if (cursor.moveToFirst()) {
            tongSoLuongDaBan = cursor.getInt(0);
        }

        cursor.close();
        return tongSoLuongDaBan;
    }

//    public ArrayList<DanhGia> getDanhGiaBymaSP1(int maSP) {
//        String sqlGH = "SELECT * FROM DanhGia WHERE maSP = ?";
//        Cursor cursor = db.rawQuery(sqlGH, new String[]{String.valueOf(maSP)});
//        ArrayList<DanhGia> arr = new ArrayList<>();
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                DanhGia danhGia = new DanhGia();
//                danhGia.setMaDG(cursor.getInt(0));
//                danhGia.setMaUser(cursor.getInt(1));
//                danhGia.setMaSP(cursor.getInt(2));
//                danhGia.setDangGia(cursor.getInt(3));
//                danhGia.setNhanXet(cursor.getString(4));
//                arr.add(danhGia);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        return arr;
//    }

}