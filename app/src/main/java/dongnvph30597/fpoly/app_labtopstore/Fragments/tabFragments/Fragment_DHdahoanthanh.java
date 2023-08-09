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


public class Fragment_DHdahoanthanh extends Fragment implements Adapter_DonHang.OnTrangThaiChangeListener {

    private RecyclerView recyclerDHdahoanthanh;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;
    int maUser;

    public Fragment_DHdahoanthanh() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_DHdahoanthanh newInstance(String param1, String param2) {
        Fragment_DHdahoanthanh fragment = new Fragment_DHdahoanthanh();
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
        return inflater.inflate(R.layout.fragment__donhang_dahoanthanh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerDHdahoanthanh = view.findViewById(R.id.recyclerDHdahoanthanh);

        FilltoRecyclerDHdaxuly();
        adapter.setOnTrangThaiChangeListener(this);
    }

    public void FilltoRecyclerDHdaxuly(){
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);
        if(maUser != -1){
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHDagoanthanhByIDUser(maUser);
            Collections.sort(arr, new Comparator<DonHang>() {
                @Override
                public int compare(DonHang dh1, DonHang dh2) {
                    return String.valueOf(dh2.getMaHD()).compareTo(String.valueOf(dh1.getMaHD()));
                }
            });
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHdahoanthanh.setAdapter(adapter);
        }else {
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHdahoanthanh();
            Collections.sort(arr, new Comparator<DonHang>() {
                @Override
                public int compare(DonHang dh1, DonHang dh2) {
                    return String.valueOf(dh2.getMaHD()).compareTo(String.valueOf(dh1.getMaHD()));
                }
            });
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHdahoanthanh.setAdapter(adapter);
        }

    }

    @Override
    public void onTrangThaiChanged(int position, int newTrangThai) {
        FilltoRecyclerDHdaxuly();
    }
}