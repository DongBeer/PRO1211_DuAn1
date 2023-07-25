package dongnvph30597.fpoly.app_labtopstore.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import dongnvph30597.fpoly.app_labtopstore.database.DB_Store;

public class ThongKeDAO {
    private SQLiteDatabase db;

    public ThongKeDAO(Context context) {
        DB_Store db_store = new DB_Store(context);
        this.db = db_store.getWritableDatabase();
    }

    public int getTotalProduct() {
        int total = 0;

        String query = "SELECT COUNT(*) FROM SanPham";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }

        cursor.close();
        return total;

    }
}