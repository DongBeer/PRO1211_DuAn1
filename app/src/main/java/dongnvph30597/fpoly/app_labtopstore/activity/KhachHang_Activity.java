package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Home;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_SanPham;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Thongtin;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Yeuthich;
import dongnvph30597.fpoly.app_labtopstore.R;

public class KhachHang_Activity extends AppCompatActivity {

    public static DrawerLayout drawerLayoutbottonnav;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);

        drawerLayoutbottonnav = findViewById(R.id.drawerlayoutbottomnav);
        bottomNavigationView = findViewById(R.id.bottonnav);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_Home()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int it = item.getItemId();
                if(it == R.id.ic_home_bottonnav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_Home()).commit();
                }else if(it == R.id.ic_search_bottonnav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_SanPham()).commit();
                }else if(it == R.id.ic_like_product){
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_Yeuthich()).commit();
                }else if(it == R.id.ic_person_bottonnav){
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_Thongtin()).commit();
                }
                return true;
            }
        });
    }
}