package dongnvph30597.fpoly.app_labtopstore.manhinhcho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAOO.AdminDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.Login_Activity;
import dongnvph30597.fpoly.app_labtopstore.model.Admin;

public class Manhinhcho1 extends AppCompatActivity {

    private AdminDAO adminDAO;
    private ArrayList<Admin> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhcho1);

        adminDAO = new AdminDAO(Manhinhcho1.this);
        arr = adminDAO.getAllAdmin();
        if(arr.size() == 0){
            adminDAO.insertadmin();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
                finish();
            }
        },2000);

    }
}