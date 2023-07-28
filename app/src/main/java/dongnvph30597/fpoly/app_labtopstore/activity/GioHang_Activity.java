package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.GioHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SharedPreferencesHelper;
import dongnvph30597.fpoly.app_labtopstore.tools.DeleteItemGioHang;

public class GioHang_Activity extends AppCompatActivity {

    private RecyclerView recyclerGioHang;
    private GioHangDAO gioHangDAO;
    private Adapter_GioHang adapter;
    private ArrayList<GioHang> arr = new ArrayList<>();

    private SharedPreferencesHelper preferencesHelper;

    private ImageView imgbackGH;
    private TextView tvTongthanhtoan;
    private TextView tvMuaHang;
    private int Tongtien = 0;
    private int maUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        recyclerGioHang = findViewById(R.id.recyclerGioHang);
        tvTongthanhtoan = findViewById(R.id.tvTongthanhtoan);
        preferencesHelper = new SharedPreferencesHelper(GioHang_Activity.this);
        imgbackGH  = findViewById(R.id.imgbackGH);
        imgbackGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMuaHang = findViewById(R.id.tvMuaHang);
        tvMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Tongtien != 0){
                    Intent intent = new Intent(GioHang_Activity.this,DatHang_Activity.class);
                    intent.putExtra("tongtien",Tongtien);
                    startActivity(intent);
                }else {
                    Toast.makeText(GioHang_Activity.this, "Bạn chưa chọn mặt hàng nào!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        FilltoRecyclerGH();

        Toast.makeText(this, ""+ arr.size(), Toast.LENGTH_SHORT).show();



    }

    public void FilltoRecyclerGH(){
        SharedPreferences preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         maUser = preferences.getInt("maUser", -1);
        gioHangDAO = new GioHangDAO(GioHang_Activity.this);
        arr = gioHangDAO.getGioHangbyIdUser(maUser);
        adapter = new Adapter_GioHang(GioHang_Activity.this, arr);
        DeleteItemGioHang deleteItemGioHang = new DeleteItemGioHang(GioHang_Activity.this,adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(deleteItemGioHang);
        itemTouchHelper.attachToRecyclerView(recyclerGioHang);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        adapter.setOnTotalPriceChangeListener(new Adapter_GioHang.OnTotalPriceChangeListener() {
            @Override
            public void onTotalPriceChange(int totalPrice) {
                tvTongthanhtoan.setText(decimalFormat.format(totalPrice) + " ₫");
                Tongtien = totalPrice;
            }
        });
        adapter.setData(arr);
        recyclerGioHang.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preferencesHelper.clearCheckedItems();
        Tongtien = 0;
        tvTongthanhtoan.setText(0 + " ₫");

        gioHangDAO = new GioHangDAO(GioHang_Activity.this);
        arr = gioHangDAO.getGioHangbyIdUser(maUser);
        adapter = new Adapter_GioHang(GioHang_Activity.this, arr);
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        adapter.setOnTotalPriceChangeListener(new Adapter_GioHang.OnTotalPriceChangeListener() {
            @Override
            public void onTotalPriceChange(int totalPrice) {
                tvTongthanhtoan.setText(decimalFormat.format(totalPrice) + " ₫");
                Tongtien = totalPrice;
            }
        });
        adapter.setData(arr);
        recyclerGioHang.setAdapter(adapter);
    }


}