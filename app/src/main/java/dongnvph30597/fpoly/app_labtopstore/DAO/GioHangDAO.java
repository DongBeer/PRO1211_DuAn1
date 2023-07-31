package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;

public class GioHangDAO {
    private SQLiteDatabase db;

    public GioHangDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db =  db_store.getWritableDatabase();
    }

    public long insert(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("maUser",gioHang.maUser);
        values.put("maSP", gioHang.maSP);
        values.put("soLuong",gioHang.soLuong);
        values.put("gia",gioHang.giaSP);
        values.put("trangThai",gioHang.trangThai);
        return db.insert("GioHang",null, values);
    }

    public long update(GioHang gioHang){
        ContentValues values = new ContentValues();
        values.put("maUser",gioHang.maUser);
        values.put("maSP", gioHang.maSP);
        values.put("soLuong",gioHang.soLuong);
        values.put("gia",gioHang.giaSP);
        values.put("trangThai",gioHang.trangThai);
        return db.update("GioHang",values,"maGH=?",new String[]{String.valueOf(gioHang.maGH)});
    }

    public int delete(String id){
        return db.delete("GioHang","maGH=?",new String[]{id});
    }

    public int deleteGioHangByTrangThaiAndMaUser(int maUser) {
        return db.delete("GioHang", "trangThai = ? AND maUser = ?", new String[]{String.valueOf(1), String.valueOf(maUser)});
    }

    public ArrayList<GioHang> getGioHangbyIdUser(int maUser) {
        String sqlGH = "SELECT * FROM GioHang WHERE maUser = ?";
        Cursor cursor = db.rawQuery(sqlGH, new String[]{String.valueOf(maUser)} );
        ArrayList<GioHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                GioHang gioHang = new GioHang();
                gioHang.setMaGH(cursor.getInt(0));
                gioHang.setMaUser(cursor.getInt(1));
                gioHang.setMaSP(cursor.getInt(2));
                gioHang.setSoLuong(cursor.getInt(3));
                gioHang.setGiaSP(cursor.getInt(4));
                arr.add(gioHang);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }

    public ArrayList<GioHang> getGioHangbyIdUserTT(int maUser) {
        String sqlGH = "SELECT * FROM GioHang WHERE maUser = ? and trangThai = 1";
        Cursor cursor = db.rawQuery(sqlGH, new String[]{String.valueOf(maUser)} );
        ArrayList<GioHang> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                GioHang gioHang = new GioHang();
                gioHang.setMaGH(cursor.getInt(0));
                gioHang.setMaUser(cursor.getInt(1));
                gioHang.setMaSP(cursor.getInt(2));
                gioHang.setSoLuong(cursor.getInt(3));
                gioHang.setGiaSP(cursor.getInt(4));
                arr.add(gioHang);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arr;
    }

    public void updateTrangThaiByMaUser(int maUser) {
        ContentValues values = new ContentValues();
        values.put("trangThai", 0); // Giá trị 0 sẽ thay thế giá trị trạng thái cũ (1)

        db.update("GioHang", values, "maUser = ?", new String[]{String.valueOf(maUser)});
        db.close();
    }

}
