package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class HoaDonChiTietDAO {
    private SQLiteDatabase db;

    public HoaDonChiTietDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
    }

    public long insert(ChiTietDonHang ctdh){
        ContentValues values = new ContentValues();
        values.put("maHD",ctdh.maDonHang);
        values.put("maSP",ctdh.maSanPham);
        values.put("soLuong",ctdh.soLuong);
        return db.insert("HoaDonChiTiet",null, values);
    }

    @SuppressLint("Range")
    public ArrayList<ChiTietDonHang> getHDCTbyIDmaHD(int maHD) {
        String sqlHDCT = "SELECT * FROM HoaDonChiTiet WHERE maHD = ?";
        Cursor cursor = db.rawQuery(sqlHDCT, new String[]{String.valueOf(maHD)} );
        ArrayList<ChiTietDonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                ChiTietDonHang ctdh = new ChiTietDonHang();
                ctdh.setMaDHCT(cursor.getInt(cursor.getColumnIndex("maHDCT")));
                ctdh.setMaDonHang(cursor.getInt(cursor.getColumnIndex("maHD")));
                ctdh.setMaSanPham(cursor.getInt(cursor.getColumnIndex("maSP")));
                ctdh.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                arr.add(ctdh);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }


    @SuppressLint("Range")
    public ArrayList<ChiTietDonHang> getAllHoaDonChiTiet() {
        ArrayList<ChiTietDonHang> arr = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonChiTiet";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ChiTietDonHang ctdh = new ChiTietDonHang();
                ctdh.setMaDHCT(cursor.getInt(cursor.getColumnIndex("maHDCT")));
                ctdh.setMaDonHang(cursor.getInt(cursor.getColumnIndex("maHD")));
                ctdh.setMaSanPham(cursor.getInt(cursor.getColumnIndex("maSP")));
                ctdh.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                arr.add(ctdh);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }

    @SuppressLint("Range")
    public ArrayList<ChiTietDonHang> getHoaDonChiTietByMaSP(int maSP) {
        String sql = "SELECT * FROM HoaDonChiTiet WHERE maSP = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(maSP)});
        ArrayList<ChiTietDonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                ChiTietDonHang ctdh = new ChiTietDonHang();
                ctdh.setMaDHCT(cursor.getInt(cursor.getColumnIndex("maHDCT")));
                ctdh.setMaDonHang(cursor.getInt(cursor.getColumnIndex("maHD")));
                ctdh.setMaSanPham(cursor.getInt(cursor.getColumnIndex("maSP")));
                ctdh.setSoLuong(cursor.getInt(cursor.getColumnIndex("soLuong")));
                arr.add(ctdh);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }
}
