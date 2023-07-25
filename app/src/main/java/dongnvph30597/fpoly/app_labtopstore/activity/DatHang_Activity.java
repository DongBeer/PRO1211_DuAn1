package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.GioHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.HoaDonChiTietDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_Hoadonchitiet;
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SharedPreferencesHelper;

public class DatHang_Activity extends AppCompatActivity {

    private TextView tvHotenSDT , tvDiachidathang, tvTongtienHD, tvTongThanhToan, tvDatHang;
    private EditText edGhichu;
    private RecyclerView recyclerHDCT;
    private ImageView imgbackDH;

    private GioHangDAO gioHangDAO;
    private ArrayList<GioHang> arr = new ArrayList<>();
    private Adapter_Hoadonchitiet adapter;

    private DonHangDAO donHangDAO;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private ArrayList<ChiTietDonHang> arrct = new ArrayList<>();

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private SharedPreferencesHelper preferencesHelper;
    int maUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dathang);
        FillbyID();
        FilltoRecycleDH();

        imgbackDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        preferencesHelper = new SharedPreferencesHelper(DatHang_Activity.this);

        int Total = getIntent().getIntExtra("tongtien",-1);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        tvTongtienHD.setText(decimalFormat.format(Total)+ " ₫");
        tvTongThanhToan.setText(decimalFormat.format(Total)+ " ₫");

        tvDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences1 = getSharedPreferences("AdminShared", Context.MODE_PRIVATE);
                String maAdmin = preferences1.getString("maAdmin",null);
                String ngay = sdf.format(calendar.getTime());
                String ghiChu = edGhichu.getText().toString().trim();
                if(ghiChu.isEmpty()){
                    ghiChu = "Nothing";
                }

                DonHang dh = new DonHang();
                dh.setMaAdmin(maAdmin);
                dh.setMaUser(maUser);
                dh.setNgay(ngay);
                dh.setTongTien(Total);
                dh.setTrangThai(0);
                dh.setGhiChu(ghiChu);
                long maHD = donHangDAO.insert(dh);

                if(maHD > 0){
                    int maHoaDon = (int) maHD;
                    for (GioHang gioHang : arr) {
                            int maSP = gioHang.getMaSP();
                            int soLuong = gioHang.getSoLuong();

                            // Thêm sản phẩm vào bảng HoaDonChiTiet
                            ChiTietDonHang chiTietDonHang = new ChiTietDonHang();
                            chiTietDonHang.setMaDonHang(maHoaDon);
                            chiTietDonHang.setMaSanPham(maSP);
                            chiTietDonHang.setSoLuong(soLuong);

                            if(hoaDonChiTietDAO.insert(chiTietDonHang) > 0){
                                Toast.makeText(DatHang_Activity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                                preferencesHelper.clearCheckedItems();
                                gioHangDAO.deleteGioHangByTrangThaiAndMaUser(maUser);
                                Intent intent = new Intent(DatHang_Activity.this, KhachHang_Activity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(DatHang_Activity.this, "Đặt hàng thất bại! + chi tiết", Toast.LENGTH_SHORT).show();
                            }

                    }
                }else {
                    Toast.makeText(DatHang_Activity.this, "Đặt hàng thất bại ", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }

    public void FilltoRecycleDH(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);
        gioHangDAO = new GioHangDAO(DatHang_Activity.this);
        arr = gioHangDAO.getGioHangbyIdUserTT(maUser);
        adapter = new Adapter_Hoadonchitiet(DatHang_Activity.this,arr);
        adapter.setData(arr);
        recyclerHDCT.setAdapter(adapter);
    }

    public void FillbyID(){
        tvHotenSDT = findViewById(R.id.tvhoten_sdt);
        tvDiachidathang = findViewById(R.id.tvdiachidathang);
        tvTongtienHD = findViewById(R.id.tvTongtienHD);
        tvTongThanhToan = findViewById(R.id.tvTongthanhtoan);
        edGhichu = findViewById(R.id.edNhapghichu);
        recyclerHDCT = findViewById(R.id.recycleDathang);
        tvDatHang = findViewById(R.id.tvDatHang);
        imgbackDH = findViewById(R.id.imgbackDH);
        donHangDAO = new DonHangDAO(DatHang_Activity.this);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(DatHang_Activity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FilltoRecycleDH();
    }
}