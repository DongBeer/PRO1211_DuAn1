package dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;

public class Fragment_DHdanggiao extends Fragment implements Adapter_DonHang.OnTrangThaiChangeListener{

    private RecyclerView recyclerDHdangGiao;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;
    int maUser;



    public Fragment_DHdanggiao() {
        // Required empty public constructor
    }


    public static Fragment_DHdanggiao newInstance() {
        Fragment_DHdanggiao fragment = new Fragment_DHdanggiao();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__dh_danggiao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerDHdangGiao = view.findViewById(R.id.recyclerDHdanggiao);
        FilltoRecyclerDHdaxuly();

        adapter.setOnTrangThaiChangeListener(this);
    }

    public void FilltoRecyclerDHdaxuly(){
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);
        if(maUser != - 1){
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHDanggiaoByIDUser(maUser);
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHdangGiao.setAdapter(adapter);
        }else {
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHByTrangthai(2);
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHdangGiao.setAdapter(adapter);
        }

    }

    @Override
    public void onTrangThaiChanged(int position, int newTrangThai) {
        if(maUser != -1){
            arr = donHangDAO.getDHDanggiaoByIDUser(maUser);
            adapter.setData(arr);
            adapter.notifyDataSetChanged();
        }else {
            // Cập nhật lại dữ liệu trong Adapter
            arr = donHangDAO.getDHByTrangthai(2);
            adapter.setData(arr);
            // Thông báo cho Adapter biết có sự thay đổi
            adapter.notifyDataSetChanged();
        }
    }
}