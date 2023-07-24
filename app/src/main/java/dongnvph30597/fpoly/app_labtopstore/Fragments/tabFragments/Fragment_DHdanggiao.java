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

public class Fragment_DHdanggiao extends Fragment implements Adapter_DonHang.OnTrangThaiChangeListener{

    private RecyclerView recyclerDHdangGiao;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;



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
        donHangDAO = new DonHangDAO(getContext());
        arr = donHangDAO.getDHdangGiao();
        adapter = new Adapter_DonHang(getContext(), arr);
        adapter.setData(arr);
        recyclerDHdangGiao.setAdapter(adapter);
    }

    @Override
    public void onTrangThaiChanged(int position, int newTrangThai) {
        FilltoRecyclerDHdaxuly();
    }
}