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

import dongnvph30597.fpoly.app_labtopstore.DAO.ThongKeDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_TopSP;
import dongnvph30597.fpoly.app_labtopstore.model.TopSP;


public class Fragment_TopSP extends Fragment {

    private ImageView imghomeTop;
    private RecyclerView recyclerListTop;
    private ThongKeDAO thongKeDAO;
    private ArrayList<TopSP> arr = new ArrayList<>();
    private Adapter_TopSP adapter;
    public Fragment_TopSP() {
        // Required empty public constructor
    }


    public static Fragment_TopSP newInstance() {
        Fragment_TopSP fragment = new Fragment_TopSP();

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
        return inflater.inflate(R.layout.fragment__top_sp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imghomeTop = view.findViewById(R.id.imgHome_Top);
        imghomeTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        recyclerListTop = view.findViewById(R.id.recyclerListTop);
        FilltoListTop();
    }
    public void FilltoListTop(){
        thongKeDAO = new ThongKeDAO(getContext());
        arr = thongKeDAO.Top5SPbanchay();
        adapter = new Adapter_TopSP(getContext(), arr);
        adapter.setData(arr);
        recyclerListTop.setAdapter(adapter);
    }
}