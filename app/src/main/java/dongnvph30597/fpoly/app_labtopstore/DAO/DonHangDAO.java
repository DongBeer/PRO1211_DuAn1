package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class DonHangDAO {
    private SQLiteDatabase db;

    public DonHangDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
    }

    public long insert(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("maUser",donHang.maUser);
        values.put("maAdmin", donHang.maAdmin);
        values.put("ngay",donHang.ngay);
        values.put("tongTien",donHang.tongTien);
        values.put("trangThai",donHang.trangThai);
        values.put("ghiChu",donHang.ghiChu);
        return db.insert("HoaDon",null, values);
    }

    public long update(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("maUser",donHang.maUser);
        values.put("maAdmin", donHang.maAdmin);
        values.put("ngay",donHang.ngay);
        values.put("tongTien",donHang.tongTien);
        values.put("trangThai",donHang.trangThai);
        values.put("ghiChu",donHang.ghiChu);
        return db.update("HoaDon",values,"maHD=?",new String[]{String.valueOf(donHang.maHD)});
    }

    public int delete(String id){
        return   db.delete("HoaDon", "maHD=?", new String[]{id});
    }

    public ArrayList<DonHang> getAllDonHang(){
        String sql = "SELECT * FROM HoaDon";
        return getData(sql);
    }

    public ArrayList<DonHang> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(String.valueOf(cursor.getString(3)));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<DonHang> getDHchoxuly() {
        String sqlDH = "SELECT * FROM HoaDon WHERE trangThai = 0";
        Cursor cursor = db.rawQuery(sqlDH,null);
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<DonHang> getDHdangGiao() {
        String sqlDH = "SELECT * FROM HoaDon WHERE trangThai = 1";
        Cursor cursor = db.rawQuery(sqlDH,null);
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<DonHang> getDHdahoanthanh() {
        String sqlDH = "SELECT * FROM HoaDon WHERE trangThai = 2";
        Cursor cursor = db.rawQuery(sqlDH,null);
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public long updateTrangThaiDonHang(int maHD, int newTrangThai) {
        ContentValues values = new ContentValues();
        values.put("trangThai", newTrangThai);
        return db.update("HoaDon", values, "maHD=?", new String[]{String.valueOf(maHD)});
    }

    public ArrayList<DonHang> getDHchoxulyByIDUser( int maUser) {
        String sqlDH = "SELECT * FROM HoaDon WHERE maUser = ? and trangThai = 0";
        Cursor cursor = db.rawQuery(sqlDH,new String[]{String.valueOf(maUser)});
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<DonHang> getDHDanggiaoByIDUser( int maUser) {
        String sqlDH = "SELECT * FROM HoaDon WHERE maUser = ? and trangThai = 1";
        Cursor cursor = db.rawQuery(sqlDH,new String[]{String.valueOf(maUser)});
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<DonHang> getDHDagoanthanhByIDUser( int maUser) {
        String sqlDH = "SELECT * FROM HoaDon WHERE maUser = ? and trangThai = 2";
        Cursor cursor = db.rawQuery(sqlDH,new String[]{String.valueOf(maUser)});
        ArrayList<DonHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                DonHang donHang = new DonHang();
                donHang.setMaHD(cursor.getInt(0));
                donHang.setMaUser(cursor.getInt(1));
                donHang.setMaAdmin(cursor.getString(2));
                donHang.setNgay(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangThai(cursor.getInt(5));
                donHang.setGhiChu(cursor.getString(6));
                arr.add(donHang);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public void deleteHoaDonChiTietByMaHD(String maHD) {
        db.delete("HoaDonChiTiet", "maHD=?", new String[]{maHD});
        db.close();
    }

}
