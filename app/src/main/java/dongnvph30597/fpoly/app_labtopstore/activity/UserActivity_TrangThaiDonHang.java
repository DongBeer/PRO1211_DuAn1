package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Thongtin;
import dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments.Fragment_DHchoxuly;
import dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments.Fragment_DHdahoanthanh;
import dongnvph30597.fpoly.app_labtopstore.Fragments.tabFragments.Fragment_DHdanggiao;
import dongnvph30597.fpoly.app_labtopstore.R;

public class UserActivity_TrangThaiDonHang extends AppCompatActivity {

    private ImageView imgbackTTDH;
    private TabLayout tabLayout_DHUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_trangthai_donhang);
        imgbackTTDH = findViewById(R.id.imgBackTTDH);
        imgbackTTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout_DHUser = findViewById(R.id.tab_layout_User);
        tabLayout_DHUser.addTab(tabLayout_DHUser.newTab().setText("Chờ xử lý"));
        tabLayout_DHUser.addTab(tabLayout_DHUser.newTab().setText("Đang vẩn chuyển"));
        tabLayout_DHUser.addTab(tabLayout_DHUser.newTab().setText("Đã giao"));

        if(tabLayout_DHUser.getTabAt(0).isSelected()){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH_User, new Fragment_DHchoxuly()).commit();
        }
        tabLayout_DHUser.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH_User, new Fragment_DHchoxuly()).commit();
                }else if(tab.getPosition() == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH_User, new Fragment_DHdanggiao()).commit();
                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragDH_User, new Fragment_DHdahoanthanh()).commit();
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