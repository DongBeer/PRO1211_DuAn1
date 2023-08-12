package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.annotation.SuppressLint;
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
        values.put("soLuong", sp.soLuong);
        values.put("imgSP", sp.imgSP);
        values.put("trangThaiSP",sp.trangThai);
        return db.insert("SanPham", null, values);
    }

    public long update(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("tenSP", sp.tenSP);
        values.put("moTa", sp.moTa);
        values.put("maTH", sp.maTH);
        values.put("giaSP", sp.giaSP);
        values.put("soLuong", sp.soLuong);
        values.put("imgSP", sp.imgSP);
        values.put("trangThaiSP",sp.trangThai);
        return db.update("SanPham",values,"maSP=?",new String[]{String.valueOf(sp.maSP)});
    }

    public long updateTrangThai(SanPham sp){
        ContentValues values = new ContentValues();
        values.put("trangThaiSP",sp.trangThai);
        return db.update("SanPham",values,"maSP=?",new String[]{String.valueOf(sp.maSP)});
    }


    public int delete(String id){
        Cursor cursor = db.rawQuery("select * from HoaDonChiTiet where maSP=?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int checkLoaisach = db.delete("SanPham","maSP=?", new String[]{id});
        if (checkLoaisach == -1)
            return 0;

        return 1;

    }

    public ArrayList<SanPham> getAllSP(){
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }

    public ArrayList<SanPham> getAllSPAdmin(){
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }

    public ArrayList<SanPham> getSanPhamByMaTH(int maTH) {
        String sql = "SELECT * FROM SanPham WHERE maTH = ?";
        String[] selectionArgs = {String.valueOf(maTH)};
        return getData(sql, selectionArgs);
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
                sp.setSoLuong(cursor.getInt(5));
                sp.setImgSP(cursor.getString(6));
                sp.setTrangThai(cursor.getInt(7));
                arr.add(sp);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<SanPham> getSPYeuthich(){
        ArrayList<SanPham> arr = new ArrayList<>();
        String sql = "select * from SanPham where trangThaiSP = 1 ";
        Cursor cursor = db.rawQuery(sql,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                SanPham sp = new SanPham();
                sp.setMaSP(cursor.getInt(0));
                sp.setTenSP(cursor.getString(1));
                sp.setMoTa(cursor.getString(2));
                sp.setMaTH(cursor.getInt(3));
                sp.setGiaSP(cursor.getInt(4));
                sp.setSoLuong(cursor.getInt(5));
                sp.setImgSP(cursor.getString(6));
                sp.setTrangThai(cursor.getInt(7));
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

    public ArrayList<SanPham> searchSanPham(String keyword) {
        String sql = "SELECT * FROM SanPham WHERE tenSP LIKE ? OR giaSP LIKE ?";
        String[] selectionArgs = new String[]{"%" + keyword + "%", "%" + keyword + "%"};
        return getData(sql, selectionArgs);
    }
    @SuppressLint("Range")
    public int getSoLuongByMaSP(int maSP) {
        int soLuong = -1; // Giá trị mặc định nếu không tìm thấy mã sản phẩm

        String[] columns = {"soLuong"};
        String selection = "maSP = ?";
        String[] selectionArgs = {String.valueOf(maSP)};

        Cursor cursor = db.query("SanPham", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            soLuong = cursor.getInt(cursor.getColumnIndex("soLuong"));
        }

        cursor.close();
        return soLuong;
    }

    public int updateSoLuong(SanPham sanPham) {

        ContentValues values = new ContentValues();
        values.put("soLuong", sanPham.getSoLuong());

        return db.update("SanPham", values, "maSP = ?", new String[] { String.valueOf(sanPham.getMaSP()) });
    }
}
