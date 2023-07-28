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

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;


public class Fragment_DHchoxuly extends Fragment implements Adapter_DonHang.OnTrangThaiChangeListener{

    private RecyclerView recyclerDHchoxuly;
    private DonHangDAO donHangDAO;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private Adapter_DonHang adapter;

    int maUser;

    public Fragment_DHchoxuly() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_DHchoxuly newInstance() {
        Fragment_DHchoxuly fragment = new Fragment_DHchoxuly();
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
        return inflater.inflate(R.layout.fragment__donhangh_choxuly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerDHchoxuly = view.findViewById(R.id.recyclerDHchoxyly);

        FilltoRecyclerDHchoxuly();

        adapter.setOnTrangThaiChangeListener(this);
    }

    public void FilltoRecyclerDHchoxuly(){
        SharedPreferences preferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);

        if(maUser != -1){
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHchoxulyByIDUser(maUser);
            adapter = new Adapter_DonHang(getContext(),arr);
            adapter.setData(arr);
            recyclerDHchoxuly.setAdapter(adapter);
        }else {
            donHangDAO = new DonHangDAO(getContext());
            arr = donHangDAO.getDHchoxuly();
            adapter = new Adapter_DonHang(getContext(), arr);
            adapter.setData(arr);
            recyclerDHchoxuly.setAdapter(adapter);
        }

    }

    @Override
    public void onTrangThaiChanged(int position, int newTrangThai) {
        FilltoRecyclerDHchoxuly();
    }
}