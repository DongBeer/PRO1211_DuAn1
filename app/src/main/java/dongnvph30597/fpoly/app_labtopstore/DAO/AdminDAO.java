package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.Admin;

public class AdminDAO {
    private SQLiteDatabase db;
    SharedPreferences sharedPreferences1;

    public AdminDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
        sharedPreferences1 = context.getSharedPreferences("AdminShared",context.MODE_PRIVATE);
    }

    public long insertadmin(){
        ContentValues values = new ContentValues();
        values.put("maAdmin","admin");
        values.put("hoTen","Nguyễn Đông");
        values.put("mkAdmin","admin");
        return db.insert("Admin",null,values);
    }

    public long update(Admin admin){
        ContentValues values = new ContentValues();
        values.put("maAdmin",admin.maAdmin);
        values.put("hoTen",admin.hoTen);
        values.put("mkAdmin",admin.mkAdmin);
        return db.update("Admin",values,"maAdmin=?",new String[]{String.valueOf(admin.maAdmin)});
    }

    public ArrayList<Admin> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<Admin> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                Admin admin = new Admin();
                admin.setMaAdmin(cursor.getString(0));
                admin.setHoTen(cursor.getString(1));
                admin.setMkAdmin(cursor.getString(2));
                arr.add(admin);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public ArrayList<Admin> getAllAdmin(){
        String sql = "SELECT * FROM Admin";
        return getData(sql);
    }

    public Admin getID(String id){
        String sql = "SELECT * FROM Admin WHERE maAdmin=?";
        List<Admin> list = getData(sql,id);
        return list.get(0);
    }
    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM Admin WHERE maAdmin=? AND mkAdmin=?";
        List<Admin> list = getData(sql,id,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }

    public int kiemTraDangNhap(String taikhoan, String matkhau) {
        Cursor cursor = db.rawQuery("select * from Admin where maAdmin = ? and mkAdmin = ?", new String[]{taikhoan, matkhau});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putString("maAdmin", cursor.getString(0));
            editor.putString("hoTen", cursor.getString(1));
            editor.putString("mkAdmin", cursor.getString(2));
            editor.commit();
            return 1;
        }else {
            return -1;
        }
    }
}
