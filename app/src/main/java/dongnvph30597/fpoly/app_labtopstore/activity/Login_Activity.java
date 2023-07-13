package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;

public class Login_Activity extends AppCompatActivity {
    private EditText edTendangnhap, edPass;
    private Button btnLogin;
    private TextView tvRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edTendangnhap = findViewById(R.id.edTendangnhap);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(Login_Activity.this, MainActivity.class);
                startActivity(aIntent);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRegister.setTextColor(Color.parseColor("#C4B0E8"));

                // Đặt thời gian chờ trước khi trở về màu cũ
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Trở về màu cũ của TextView
                        tvRegister.setTextColor(Color.parseColor("#7230B3"));
                    }
                }, 10); // Thời gian chờ (đơn vị: milliseconds)
                Intent mIntent = new Intent(Login_Activity.this, Register_activity.class);
                startActivity(mIntent);
            }
        });
    }
}