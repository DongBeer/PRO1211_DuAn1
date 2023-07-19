package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments.Fragment_DHchoxuly;
import dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments.Fragment_DHdahoanthanh;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;


public class Fragment_QuanlyDonHang extends Fragment {

    private ImageView imgHomeDH;
    private TabLayout tabLayout;
    public Fragment_QuanlyDonHang() {
        // Required empty public constructor
    }


    public static Fragment_QuanlyDonHang newInstance(String param1, String param2) {
        Fragment_QuanlyDonHang fragment = new Fragment_QuanlyDonHang();

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
        return inflater.inflate(R.layout.fragment__quanly_donhang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgHomeDH = view.findViewById(R.id.imgHome_qldh);
        imgHomeDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Chờ xử lý"));
        tabLayout.addTab(tabLayout.newTab().setText("Đâ hoàn thành"));

        if(tabLayout.getTabAt(0).isSelected()){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH, new Fragment_DHchoxuly()).commit();
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH, new Fragment_DHchoxuly()).commit();
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH, new Fragment_DHdahoanthanh()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}