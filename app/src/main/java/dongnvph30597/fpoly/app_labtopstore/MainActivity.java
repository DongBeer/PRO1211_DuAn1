package dongnvph30597.fpoly.app_labtopstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import dongnvph30597.fpoly.app_labtopstore.Fragments.Fragment_QuanlyKhachHang;

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
    }
}