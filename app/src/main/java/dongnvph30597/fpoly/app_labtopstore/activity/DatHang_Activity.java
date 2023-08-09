package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_Hoadonchitiet;
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.SharedPreferencesHelper;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class DatHang_Activity extends AppCompatActivity {

    private TextView tvTongtienHD, tvTongThanhToan, tvSuadc, tvHoten, tvSDTDC, tvDiachidathang ;
    private EditText edGhichu;
    private RecyclerView recyclerHDCT;
    private ImageView imgbackDH;
    private LinearLayout lnbtnDathang;
    private Spinner spinnerPTTT;

    private EditText edTenKHSuaDC, edSoDTSuaDC, edDCSuaDC;
    private Button btnSuaDC,btnCanclerSuaDC;

    private GioHangDAO gioHangDAO;
    private ArrayList<GioHang> arr = new ArrayList<>();
    private Adapter_Hoadonchitiet adapter;

    private UserDAO userDAO;

    private DonHangDAO donHangDAO;
    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private ArrayList<ChiTietDonHang> arrct = new ArrayList<>();

    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
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
                int check = getIntent().getIntExtra("check",-1);
                if(check == 5){
                    gioHangDAO.deleteGioHangByTrangThaiAndMaUser(maUser);
                }
                finish();
            }
        });

        User user = userDAO.getID(String.valueOf(maUser));
        String ten = user.getHoTen();
        String sdt = user.getSoDT();
        String dc = user.getDiaChi();

        tvHoten.setText(ten);
        tvSDTDC.setText(sdt);
        tvDiachidathang.setText(dc);

        tvSuadc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog mdialog = new Dialog(DatHang_Activity.this);
                mdialog.setContentView(R.layout.layout_dialog_update_diachi);
                mdialog.getWindow().setGravity(Gravity.TOP);
                mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                edTenKHSuaDC = mdialog.findViewById(R.id.edTenKHsuaDC);
                edSoDTSuaDC = mdialog.findViewById(R.id.edSoDTKHsuaDC);
                edDCSuaDC = mdialog.findViewById(R.id.edDCKHsuaDC);
                btnSuaDC = mdialog.findViewById(R.id.btnSuaDC);
                btnCanclerSuaDC = mdialog.findViewById(R.id.btnCancleSuaDC);

                edTenKHSuaDC.setText(tvHoten.getText().toString());
                edSoDTSuaDC.setText(tvSDTDC.getText().toString());
                edDCSuaDC.setText(tvDiachidathang.getText().toString());

                btnSuaDC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String t = edTenKHSuaDC.getText().toString().trim();
                        String s = edSoDTSuaDC.getText().toString().trim();
                        String d = edDCSuaDC.getText().toString().trim();
                        if(t.isEmpty() && s.isEmpty() && d.isEmpty()){
                            Toast.makeText(DatHang_Activity.this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String phoneNumberPattern = "^0[0-9]{9}$";
                        if(!s.matches(phoneNumberPattern)){
                            Toast.makeText(DatHang_Activity.this, "Nhập sai định dạng số điện thoại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        User user1 = new User();
                        user1.setMaUser(maUser);
                        user1.setHoTen(t);
                        user1.setSoDT(s);
                        user1.setDiaChi(d);
                        UserDAO userDAO = new UserDAO(DatHang_Activity.this);
                        if(userDAO.updateDC(user1) > 0){
                            Toast.makeText(DatHang_Activity.this, "Thêm địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                            tvHoten.setText(t);
                            tvSDTDC.setText(s);
                            tvDiachidathang.setText(d);
                            mdialog.dismiss();
                        }else {
                            Toast.makeText(DatHang_Activity.this, "Fail →", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btnCanclerSuaDC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();
                    }
                });


                mdialog.show();
            }
        });

        ArrayList<String> arrPT = new ArrayList<>();
        arrPT.add("Thanh toán khi nhận hàng");
        arrPT.add("Thanh toán bằng ví LapStore Pay");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(DatHang_Activity.this, android.R.layout.simple_spinner_item, arrPT);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPTTT.setAdapter(adapter);

        spinnerPTTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPhuongThuc = arrPT.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        preferencesHelper = new SharedPreferencesHelper(DatHang_Activity.this);

        int Total = getIntent().getIntExtra("tongtien",-1);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        tvTongtienHD.setText(decimalFormat.format(Total)+ " ₫");
        tvTongThanhToan.setText(decimalFormat.format(Total)+ " ₫");

        lnbtnDathang.setOnClickListener(new View.OnClickListener() {
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
                dh.setTrangThaiDG(0);
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
                                SanPhamDAO sanPhamDAO = new SanPhamDAO(DatHang_Activity.this);
                                int slbd = sanPhamDAO.getSoLuongByMaSP(maSP);
                                int updatesl = slbd - soLuong;

//                                if(slbd == soLuong){
//                                    SanPham sanPham = new SanPham();
//                                    sanPham.setMaSP(maSP);
//                                    sanPham.setTrangThai(2);
//                                    sanPhamDAO.updateTrangThai(sanPham);
//                                }

                                SanPham sanPham = new SanPham();
                                sanPham.setMaSP(maSP);
                                sanPham.setSoLuong(updatesl);
                                sanPhamDAO.updateSoLuong(sanPham);
                                gioHangDAO.deleteGioHangByTrangThaiAndMaUser(maUser);
                                preferencesHelper.clearCheckedItems();
                                finish();
                                Intent intent = new Intent(DatHang_Activity.this, UserActivity_TrangThaiDonHang.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
        tvHoten = findViewById(R.id.tvhotenDC);
        tvSDTDC = findViewById(R.id.tvsdtDC);
        tvSuadc = findViewById(R.id.tvSuadiachidathang);
        tvDiachidathang = findViewById(R.id.tvdiachidathang);
        tvTongtienHD = findViewById(R.id.tvTongtienHD);
        tvTongThanhToan = findViewById(R.id.tvTongthanhtoan);
        edGhichu = findViewById(R.id.edNhapghichu);
        recyclerHDCT = findViewById(R.id.recycleDathang);
        lnbtnDathang = findViewById(R.id.lnbtnDathang);
        imgbackDH = findViewById(R.id.imgbackDH);
        spinnerPTTT = findViewById(R.id.spnPTTT);
        preferencesHelper = new SharedPreferencesHelper(DatHang_Activity.this);
        donHangDAO = new DonHangDAO(DatHang_Activity.this);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(DatHang_Activity.this);
        userDAO = new UserDAO(DatHang_Activity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}