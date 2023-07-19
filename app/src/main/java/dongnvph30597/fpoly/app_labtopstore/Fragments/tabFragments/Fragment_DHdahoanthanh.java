package dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;


public class Fragment_DHdahoanthanh extends Fragment {

    private RecyclerView recyclerDHdahoanthanh;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;

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
    }

    public void FilltoRecyclerDHdaxuly(){
        donHangDAO = new DonHangDAO(getContext());
        arr = donHangDAO.getDHdahoanthanh();
        adapter = new Adapter_DonHang(getContext(), arr);
        adapter.setData(arr);
        recyclerDHdahoanthanh.setAdapter(adapter);
    }
}