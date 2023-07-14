package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class UserDAO {
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        db = db_store.getWritableDatabase();
    }

    public long insert(User user){
        ContentValues values = new ContentValues();
        values.put("hoTen", user.hoTen);
        values.put("tkUser",user.tenDangnhap);
        values.put("mkUser",user.matkhau);
        values.put("soDT",user.soDT);
        values.put("diaChi",user.diaChi);
        return db.insert("User",null,values);
    }
    public long update(User user){
        ContentValues values = new ContentValues();
        values.put("hoTen", user.hoTen);
        values.put("tkUser",user.tenDangnhap);
        values.put("mkUser",user.matkhau);
        values.put("soDT",user.soDT);
        values.put("diaChi",user.diaChi);
        return db.update("User",values,"maUser=?",new String[]{String.valueOf(user.maUser)});
    }

    public ArrayList<User> getAllTV(){
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

}
