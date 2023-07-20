package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class SanPhamDAO {
    private SQLiteDatabase db;

    public SanPhamDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
    }

    public long insert(SanPham sp) {
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.tenSP);
        values.put("moTa", sp.moTa);
        values.put("maTH", sp.maTH);
        values.put("giaSP", sp.giaSP);
        values.put("loaiSP", sp.loaiSP);
        values.put("soLuong", sp.soLuong);
        values.put("imgSP", sp.imgSP);
        return db.insert("SanPham", null, values);
    }

    public long update(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.tenSP);
        values.put("moTa", sp.moTa);
        values.put("maTH", sp.maTH);
        values.put("giaSP", sp.giaSP);
        values.put("loaiSP", sp.loaiSP);
        values.put("soLuong", sp.soLuong);
        values.put("imgSP", sp.imgSP);
        return db.update("SanPham",values,"maUser=?",new String[]{String.valueOf(sp.maSP)});
    }

    public int delete(String id){
        return db.delete("SanPham","maSP=?", new String[]{id});
    }

    public ArrayList<SanPham> getAllSP(){
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }

    public ArrayList<SanPham> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<SanPham> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                SanPham sp = new SanPham();
                sp.setMaSP(cursor.getInt(0));
                sp.setTenSP(cursor.getString(1));
                sp.setMoTa(cursor.getString(2));
                sp.setMaTH(cursor.getInt(3));
                sp.setGiaSP(cursor.getInt(4));
                sp.setLoaiSP(cursor.getString(5));
                sp.setSoLuong(cursor.getInt(6));
                sp.setImgSP(cursor.getString(7));
                arr.add(sp);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public SanPham getID(String id){
        String sql = "SELECT * FROM SanPham WHERE maSP=?";
        List<SanPham> list = getData(sql,id);
        return list.get(0);
    }
}