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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Register_activity extends AppCompatActivity {

    private TextInputEditText edHotendk, edTenDNdk, edPassdk, edSoDTdk, edDiachidk;
    private TextInputLayout textHoten, textTenDN, textPass, textSDT, textDiachi;
    private Button btnDangky;
    private TextView tvTextDangnhap;

    private UserDAO userDAO;
    private ArrayList<User> listUser = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FindbyID();


        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkRegister();
            }
        });

        tvTextDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTextDangnhap.setTextColor(Color.parseColor("#C4B0E8"));

                // Đặt thời gian chờ trước khi trở về màu cũ
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Trở về màu cũ của TextView
                        tvTextDangnhap.setTextColor(Color.parseColor("#7230B3"));
                    }
                }, 15); // Thời gian chờ (đơn vị: milliseconds)
                Intent mIntent = new Intent(Register_activity.this,Login_Activity.class);
                startActivity(mIntent);
            }
        });
    }


    public void FindbyID() {
        edHotendk = findViewById(R.id.edhoTen);
        edTenDNdk = findViewById(R.id.edDKtendangnhap);
        edPassdk = findViewById(R.id.edDKpass);
        edSoDTdk = findViewById(R.id.edDKsoDT);
        edDiachidk = findViewById(R.id.edDKdiachi);

        textHoten = findViewById(R.id.textInputLayouthoten);
        textTenDN = findViewById(R.id.textInputLayouttdn);
        textPass = findViewById(R.id.textInputLayoutmk);
        textSDT = findViewById(R.id.textInputLayoutsdt);
        textDiachi = findViewById(R.id.textInputLayoutdchi);

        btnDangky = findViewById(R.id.btn_Dangky);

        tvTextDangnhap = findViewById(R.id.tvtext_Dangnhap);

        userDAO = new UserDAO(Register_activity.this);
    }

    private boolean validateInput(String input, String errorMessage, TextInputEditText editText) {
        if (input.isEmpty()) {
            editText.setError(errorMessage);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    public void checkRegister(){
        String ht = edHotendk.getText().toString().trim();
        String tdn = edTenDNdk.getText().toString().trim();
        String pass = edPassdk.getText().toString().trim();
        String sdt = edSoDTdk.getText().toString().trim();
        String dc = edDiachidk.getText().toString().trim();

        listUser = userDAO.getAllUser();
        String phoneNumberPattern = "^0[0-9]{9}$";

        if (validateInput(ht, "Vui lòng nhập họ tên", edHotendk)) {
            return;
        }

        if (validateInput(tdn, "Vui lòng nhập tên đăng nhập", edTenDNdk)) {
            return;
        }

        if (validateInput(pass, "Vui lòng nhập mật khẩu", edPassdk)) {
            return;
        }
        if (validateInput(sdt, "Vui lòng nhập số điện thoại", edSoDTdk)) {
            return;
        }
        if (!sdt.matches(phoneNumberPattern)) {
            edSoDTdk.setError("Nhập sai định dạng số điện thoại");
            return;
        }
        if (validateInput(dc, "Vui lòng nhập địa chỉ", edDiachidk)) {
            return;
        }
        for (User user : listUser) {
            if (user.getTenDangnhap().equals(tdn)) {
                Toast.makeText(Register_activity.this, "Đã tồn tại tên đăng nhập " + tdn, Toast.LENGTH_SHORT).show();
                edTenDNdk.requestFocus();
                return;
            }
        }

        User u = new User();
        u.setHoTen(ht);
        u.setTenDangnhap(tdn);
        u.setMatkhau(pass);
        u.setSoDT(sdt);
        u.setDiaChi(dc);
        u.setImgUser(String.valueOf(R.drawable.user_login));
        if (userDAO.insert(u) > 0) {
//            Toast.makeText(Register_activity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(Register_activity.this, Login_Activity.class);
            mIntent.putExtra("maUser",u.getMaUser());
            Toast.makeText(this, ""+u.getMaUser(), Toast.LENGTH_SHORT).show();
            startActivity(mIntent);
        } else {
            Toast.makeText(Register_activity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean backPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            finishAffinity();
            return;
        }

        backPressedOnce = true;
        Toast.makeText(this, "Chạm lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressedOnce = false;
            }
        }, 2000); // Thời gian giới hạn để chạm lần tiếp theo
    }
}