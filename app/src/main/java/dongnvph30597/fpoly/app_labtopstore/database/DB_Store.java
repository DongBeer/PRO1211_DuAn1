package dongnvph30597.fpoly.app_labtopstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class DB_Store extends SQLiteOpenHelper {
    public static final String NAME = "db_store";
    public static final int VERSION = 1;

    public DB_Store(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableAdmin = "create table Admin ("+
                "maAdmin text PRIMARY KEY, " +
                "hoTen text NOT NULL, " +
                "mkAdmin text NOT NULL)";
        sqLiteDatabase.execSQL(createTableAdmin);

        String createTableUser= "create table User (" +
                "maUser INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen text NOT NULL, " +
                "tkUser text NOT NULL, " +
                "mkUser text NOT NULL, " +
                "soDT text NOT NULL,"+
                "diaChi text NOT NULL," +
                "imgUser text NOT NULL)";
        sqLiteDatabase.execSQL(createTableUser);

        String createTableThuonghieu = "create table ThuongHieu (" +
                "maTH INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenTH text NOT NULL," +
                "imgTH Text NOT NULL)";
        sqLiteDatabase.execSQL(createTableThuonghieu);

        String createTableSP = "create table SanPham (" +
                "maSP INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSP text NOT NULL, " +
                "moTa text NOT NULL,"+
                "maTH INTEGER REFERENCES ThuongHieu(maTH),"+
                "giaSP INTEGER NOT NULL,"+
                "loaiSP text NOT NULL, " +  //ví dụ như: Gaming, Sinh Viên - Văn Phòng, Đồ Họa ....
                "soLuong INTEGER NOT NULL, " +
                "imgSP INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(createTableSP);

        String createTableHD = "create table HoaDon (" +
                "maHD INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maUser INTEGER REFERENCES User(maUser)," +
                "maAdmin text REFERENCES Admin(maAdmin)," +
                "ngay date NOT NULL," +
                "tongTien INTEGER NOT NULL," +
                "trangThai INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(createTableHD);

        String createTableHDCT = "create table HoaDonChiTiet (" +
                "maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maHD INTEGER REFERENCES HoaDon(maHD)," +
                "maSP INTEGER REFERENCES SanPham(maSP)," +
                "soLuong INTEGER NOT NULL," +
                "ghiChu text NOT NULL)";
        sqLiteDatabase.execSQL(createTableHDCT);

        String createTableDanhGia = "CREATE TABLE DanhGia (" +
                "maDG INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maUser INTEGER REFERENCES User(maUser), " +
                "maSP INTEGER REFERENCES SanPham(maSP), " +
                "danhGia INTEGER NOT NULL, " +
                "nhanXet TEXT not null)";
        sqLiteDatabase.execSQL(createTableDanhGia);

        String addHD = "INSERT INTO Admin (maAdmin, hoTen,mkAdmin) VALUES "
                + "('admin1','Admin1','admin1')";
        sqLiteDatabase.execSQL(addHD);

        String addPM = "INSERT INTO User (maUser, hoTen,tkUser, mkUser, soDT, diaChi," + User.COL_PHOTO +") VALUES "
                + "(1, 'Nguyễn Đông','kh01','a','0988171166','59 Mễ Trì'," + R.drawable.background05+ "), "
                + "(2, 'Nguyễn Luân','kh02','b','0356642354','31 Di Trạch'," + R.drawable.background05+ ")";
        sqLiteDatabase.execSQL(addPM);

        String addADM = "INSERT INTO HoaDon (maHD, maUser,maAdmin, ngay, tongTien, trangThai) VALUES "
                + "(1, 1,'admin1','2023-07-18',20000,0), "
                + "(2, 2,'admin1','2023-07-18',20000,0)";

        sqLiteDatabase.execSQL(addADM);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableQL = "drop table if exists QuanLy";
        sqLiteDatabase.execSQL(dropTableQL);
        String dropTableKH = "drop table if exists KhachHang";
        sqLiteDatabase.execSQL(dropTableKH);
        String dropTableLoaiSP = "drop table if exists ThuongHieu";
        sqLiteDatabase.execSQL(dropTableLoaiSP);
        String dropTableLSP = "drop table if exists SanPham";
        sqLiteDatabase.execSQL(dropTableLSP);
        String dropTableHD = "drop table if exists HoaDon";
        sqLiteDatabase.execSQL(dropTableHD);
        String dropTableHDCT = "drop table if exists HoaDonChiTiet";
        sqLiteDatabase.execSQL(dropTableHDCT);
        String dropTableDanhGia = "drop table if exists DanhGia";
        sqLiteDatabase.execSQL(dropTableDanhGia);

        onCreate(sqLiteDatabase);
    }
}
