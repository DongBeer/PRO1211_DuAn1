package dongnvph30597.fpoly.app_labtopstore.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class ThuongHieuDao {
    private SQLiteDatabase db;

    public ThuongHieuDao(Context context) {
        DB_Store myDatabase = new DB_Store(context);
        db = myDatabase.getWritableDatabase();
    }

    public ArrayList<ThuongHieu> selectAll(){
        ArrayList<ThuongHieu> list = new ArrayList<ThuongHieu>();
        Cursor cursor = db.rawQuery("select * from ThuongHieu", null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                ThuongHieu obj = new ThuongHieu();
                obj.setMaTH(cursor.getInt(0));
                obj.setTenTH(cursor.getString(1));
                obj.setImgTH(cursor.getString(2));
                list.add(obj);
                cursor.moveToNext();
            }
        }

        return list;
    }

    public long insert (ThuongHieu obj){
        ContentValues values = new ContentValues();
        values.put("tenTH", obj.getTenTH());
        values.put("imgTH", obj.getImgTH());
        return db.insert("ThuongHieu", null, values);
    }
}
