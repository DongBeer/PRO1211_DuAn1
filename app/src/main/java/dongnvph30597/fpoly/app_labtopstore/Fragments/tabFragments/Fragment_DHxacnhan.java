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


public class Fragment_DHxacnhan extends Fragment implements Adapter_DonHang.OnTrangThaiChangeListener{

    private RecyclerView recyclerDHXacnhan;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;
    int maUser;

    public Fragment_DHxacnhan() {
        // Required empty public constructor
    }


    public static Fragment_DHxacnhan newInstance() {
        Fragment_DHxacnhan fragment = new Fragment_DHxacnhan();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_frangment_dh_xacnhan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerDHXacnhan = view.findViewById(R.id.recyclerDHXacnhan);

        FilltolistDHxacnhan();
        adapter.setOnTrangThaiChangeListener(this);
    }

    public void FilltolistDHxacnhan(){
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);

        if(maUser != -1){
            donHangDAO = new DonHangDAO(getContext());
            arr.clear();
            arr = donHangDAO.getDHXacnhanbyUser(maUser);
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHXacnhan.setAdapter(adapter);
        }else {
            donHangDAO = new DonHangDAO(getContext());
            arr.clear();
            arr = donHangDAO.getDHByTrangthai(1);
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHXacnhan.setAdapter(adapter);
        }

    }

    @Override
    public void onTrangThaiChanged(int position, int newTrangThai) {
        if(maUser != -1){
            arr = donHangDAO.getDHXacnhanbyUser(maUser);
            adapter.setData(arr);
            adapter.notifyDataSetChanged();
        }else {
            // Cập nhật lại dữ liệu trong Adapter
            arr = donHangDAO.getDHByTrangthai(1);
            adapter.setData(arr);
            // Thông báo cho Adapter biết có sự thay đổi
            adapter.notifyDataSetChanged();
        }
    }

}