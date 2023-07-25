package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Home;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_SanPham;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Thongtin;
import dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments.UserFragment_Yeuthich;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
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
                }else if(it == R.id.ic_cart_bottonnav){
                    Intent aIntent = new Intent(KhachHang_Activity.this,GioHang_Activity.class);
                    startActivity(aIntent);
                }
                else if(it == R.id.ic_person_bottonnav){
//                    getSupportFragmentManager().beginTransaction().replace(R.id.user_container,new UserFragment_Thongtin()).commit();

                    Logout();
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát khỏi ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Không",null);
        builder.show();
    }

    public void Logout(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent mIntent = new Intent(KhachHang_Activity.this, Login_Activity.class);
                mIntent.putExtra("RESET_LOGIN_STATE", true);
                startActivity(mIntent);
            }
        },1000);
    }


}