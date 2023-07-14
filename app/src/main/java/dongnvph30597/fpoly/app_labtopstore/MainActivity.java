package dongnvph30597.fpoly.app_labtopstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import dongnvph30597.fpoly.app_labtopstore.Fragments.Fragment_QuanLyLoaiSp;
import dongnvph30597.fpoly.app_labtopstore.Fragments.Fragment_QuanlyDonHang;
import dongnvph30597.fpoly.app_labtopstore.Fragments.Fragment_QuanlyKhachHang;
import dongnvph30597.fpoly.app_labtopstore.Fragments.Fragment_QuanlySanPham;

public class MainActivity extends AppCompatActivity {

    public static DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView tvuser,tvuserma;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new Fragment_QuanlyKhachHang()).commit();
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int it = item.getItemId();
                if(it == R.id.ic_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new Fragment_QuanlyDonHang()).commit();
                    drawerLayout.close();
                }else if(it == R.id.ic_type_product){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new Fragment_QuanLyLoaiSp()).commit();
                    drawerLayout.close();
                }else if(it == R.id.ic_product){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new Fragment_QuanlySanPham()).commit();
                    drawerLayout.close();
                }else if(it == R.id.ic_client){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_main,new Fragment_QuanlyKhachHang()).commit();
                    drawerLayout.close();
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
}