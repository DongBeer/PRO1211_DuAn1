package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class UserDAO {
    private SQLiteDatabase db;
    SharedPreferences sharedPreferences;

    public UserDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
        sharedPreferences = context.getSharedPreferences("MyPrefs",context.MODE_PRIVATE);
    }

    public long insert(User user){
        ContentValues values = new ContentValues();
        values.put("hoTen", user.hoTen);
        values.put("tkUser",user.tenDangnhap);
        values.put("mkUser",user.matkhau);
        values.put("soDT",user.soDT);
        values.put("diaChi",user.diaChi);
        values.put("imgUser",user.imgUser);
        return db.insert("User",null,values);
    }
    public long update(User user){
        ContentValues values = new ContentValues();
        values.put("hoTen", user.hoTen);
        values.put("tkUser",user.tenDangnhap);
        values.put("mkUser",user.matkhau);
        values.put("soDT",user.soDT);
        values.put("diaChi",user.diaChi);
        values.put("imgUser",user.imgUser);
        return db.update("User",values,"maUser=?",new String[]{String.valueOf(user.maUser)});
    }

    public ArrayList<User> getAllUser(){
        String sql = "SELECT * FROM User";
        return getData(sql);
    }

    public ArrayList<User> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<User> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                User user = new User();
                user.setMaUser(cursor.getInt(0));
                user.setHoTen(cursor.getString(1));
                user.setTenDangnhap(cursor.getString(2));
                user.setMatkhau(cursor.getString(3));
                user.setSoDT(cursor.getString(4));
                user.setDiaChi(cursor.getString(5));
                user.setImgUser(cursor.getString(6));
                arr.add(user);
                cursor.moveToNext();
            }
        }
        return arr;
    }

    public int checkLogin(String tk, String password){
        String sql = "SELECT * FROM User WHERE tkUser=? AND mkUser=?";
        List<User> list = getData(sql,tk,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }

    public User getID(String id){
        String sql = "SELECT * FROM User WHERE maUser=?";
        List<User> list = getData(sql,id);
        return list.get(0);
    }

    @SuppressLint("Range")
    public int getUserIdByUserName(String tkUser) {
        String[] projection = { "maUser" };
        String selection = "tkUser = ?";
        String[] selectionArgs = { tkUser };
        Cursor cursor = db.query("User", projection, selection, selectionArgs, null, null, null);
        int maUser = -1; // Giá trị mặc định nếu không tìm thấy

        if (cursor.moveToFirst()) {
            maUser = cursor.getInt(cursor.getColumnIndex("maUser"));
        }

        cursor.close();
        return maUser;
    }

    public int kiemTraDangNhap(String taikhoan, String matkhau) {
        Cursor cursor = db.rawQuery("select * from User where tkUser = ? and mkUser = ?", new String[]{taikhoan, matkhau});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("maUser", cursor.getInt(0));
                editor.putString("hoTen", cursor.getString(1));
                editor.putString("tkUser", cursor.getString(2));
                editor.putString("mkUser", cursor.getString(3));
                editor.putString("soDT", cursor.getString(4));
                editor.putString("diaChi", cursor.getString(5));
                editor.putString("imgUser", cursor.getString(6));
                editor.commit();
                return 1;
            }else {
                return -1;
            }
        }

}
