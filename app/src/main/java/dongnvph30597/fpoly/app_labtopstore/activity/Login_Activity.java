package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dongnvph30597.fpoly.app_labtopstore.DAO.AdminDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;

public class Login_Activity extends AppCompatActivity {
    private EditText edTendangnhap, edPass;
    private Button btnLogin;
    private TextView tvRegister;
    private CheckBox ckbSavepass;

    private AdminDAO adminDAO;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FindbyID();

        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        edTendangnhap.setText(pref.getString("USERNAME",""));
        edPass.setText(pref.getString("PASSWORD",""));
        ckbSavepass.setChecked(pref.getBoolean("REMEMBER",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
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
                }, 15); // Thời gian chờ (đơn vị: milliseconds)
                Intent mIntent = new Intent(Login_Activity.this, Register_activity.class);
                startActivity(mIntent);
            }
        });
    }

    private void FindbyID(){
        edTendangnhap = findViewById(R.id.edTendangnhap);
        edPass = findViewById(R.id.edPass);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tvRegister);
        ckbSavepass = findViewById(R.id.ckbSavepass);

        adminDAO = new AdminDAO(Login_Activity.this);
        userDAO = new UserDAO(Login_Activity.this);
    }
    public void checkLogin(){
        String strUser = edTendangnhap.getText().toString();
        String strPass = edPass.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ((adminDAO.kiemTraDangNhap(strUser, strPass) > 0) ) {
            Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
            rememberUser(strUser, strPass, ckbSavepass.isChecked());
            Intent intent = new Intent(Login_Activity.this, MainActivity.class);
            intent.putExtra("maAdmin", strUser);
            startActivity(intent);
            finish();

        }else if((userDAO.kiemTraDangNhap(strUser,strPass) > 0)){
            Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
            rememberUser(strUser, strPass, ckbSavepass.isChecked());
            Intent mIntent = new Intent(Login_Activity.this,KhachHang_Activity.class);
            startActivity(mIntent);
            finish();
        }else {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
        }
    }

    public void rememberUser(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){
            editor.clear();
        }else {
            editor.putString("USERNAME",u);
            editor.putString("PASSWORD",p);
            editor.putBoolean("REMEMBER",status);
        }
        editor.commit();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát khỏi ứng dụng không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setNegativeButton("Không",null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean resetLoginState = getIntent().getBooleanExtra("RESET_LOGIN_STATE", false);
        if (resetLoginState) {
            edTendangnhap.setText("");
            edPass.setText("");
            ckbSavepass.setChecked(false);
        }
    }
}