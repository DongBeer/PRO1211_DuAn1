package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;
import dongnvph30597.fpoly.app_labtopstore.model.User;

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
    public int update(ThuongHieu obj){
        ContentValues values = new ContentValues();
        values.put("tenTH", obj.getTenTH());
        values.put("imgTH", obj.getImgTH());
        return db.update("ThuongHieu", values, "maTH=?", new String[]{String.valueOf(obj.getMaTH())});
    }

    public int delete(String id){
        return db.delete("ThuongHieu","maTH=?", new String[]{id});
    }

    public ThuongHieu getID(String id){
        String sql = "SELECT * FROM ThuongHieu WHERE maTH=?";
        List<ThuongHieu> list = getData(sql,id);
        return list.get(0);
    }

    public ArrayList<ThuongHieu> getData(String sql, String...selectionArgs) {
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        ArrayList<ThuongHieu> arr = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()){
                ThuongHieu thuongHieu = new ThuongHieu();
                thuongHieu.setMaTH(cursor.getInt(0));
                thuongHieu.setTenTH(cursor.getString(1));
                thuongHieu.setImgTH(cursor.getString(2));
                arr.add(thuongHieu);
                cursor.moveToNext();
            }
        }
        return arr;
    }
}
