package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_KhachHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;


public class Fragment_QuanlyKhachHang extends Fragment {

    private ImageView imghome;
    private RecyclerView recyclerListKH;

    private UserDAO userDAO;
    private ArrayList<User> arr = new ArrayList<>();
    private Adapter_KhachHang adapter;

    public Fragment_QuanlyKhachHang() {
        // Required empty public constructor
    }


    public static Fragment_QuanlyKhachHang newInstance(String param1, String param2) {
        Fragment_QuanlyKhachHang fragment = new Fragment_QuanlyKhachHang();

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
        return inflater.inflate(R.layout.fragment__quanly_khachhang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imghome = view.findViewById(R.id.imgHome_qlkh);
        recyclerListKH = view.findViewById(R.id.recyclerListKH);

        fillListKH();

        imghome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    public void fillListKH(){
        userDAO = new UserDAO(getContext());
        arr = userDAO.getAllUser();
        adapter = new Adapter_KhachHang(getContext(),arr);
        adapter.setData(arr);
        recyclerListKH.setAdapter(adapter);
    }
}