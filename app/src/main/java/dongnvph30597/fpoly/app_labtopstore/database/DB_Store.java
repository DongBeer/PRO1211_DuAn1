package dongnvph30597.fpoly.app_labtopstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Store extends SQLiteOpenHelper {
    public static final String NAME = "db_store";
    public static final int VERSION = 1;

    public DB_Store(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuanly = "create table QuanLy (" +
                "maNV text PRIMARY KEY, " +
                "hoTen text NOT NULL, " +
                "matKhau text NOT NULL)";
        sqLiteDatabase.execSQL(createQuanly);

        String createTableKH = "create table KhachHang (" +
                "maKH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen text NOT NULL, " +
                "soDT INTEGER NOT NULL,"+
                "diaChi text NOT NULL)";
        sqLiteDatabase.execSQL(createTableKH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
