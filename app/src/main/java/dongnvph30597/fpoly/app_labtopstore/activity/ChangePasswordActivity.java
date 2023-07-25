package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtOldPassword, edtNewPassword, edtReEnterPassword;
    private TextView tvError;
    private ImageView icBack;
    private Button btnCancel, btnSave;
    private UserDAO dao;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        icBack  =findViewById(R.id.ic_back_password);
        tvError = findViewById(R.id.tv_error);
        edtOldPassword = findViewById(R.id.edt_old_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtReEnterPassword = findViewById(R.id.edt_re_new_password);
        btnCancel = findViewById(R.id.btn_cancel_password);
        btnSave = findViewById(R.id.btn_save_password);
        dao = new UserDAO(this);
        Log.d( "ChangeProfileActivity ",UserDAO.idUser+"");
        user = dao.getUserById(UserDAO.idUser);
        Log.d("BBB", user.toString());

        icBack.setOnClickListener(v -> finish());

        btnCancel.setOnClickListener(v -> clearText());

        btnSave.setOnClickListener(v -> {
            String oldPassword = edtOldPassword.getText().toString();
            String newPassword = edtNewPassword.getText().toString();
            String reNewPassword = edtReEnterPassword.getText().toString();
            String strPass = user.getMatkhau();
            if (validate(strPass, oldPassword, newPassword, reNewPassword)){
                user.setMatkhau(newPassword);
                dao.update(user);
                Toast.makeText(this, "đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clearText() {
        edtOldPassword.setText("");
        edtNewPassword.setText("");
        edtReEnterPassword.setText("");
    }

    private boolean validate(String strPass,String oldPassword, String newPassword, String reNewPassword){

        if (oldPassword.isEmpty() && newPassword.isEmpty() && reNewPassword.isEmpty()){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("không được để trống");
            return false;
        }else {
            tvError.setVisibility(View.GONE);
        }

        if (!strPass.equals(oldPassword)){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("không đúng mật khẩu");
            return false;
        }else {
            tvError.setVisibility(View.GONE);
        }

        if (oldPassword.equals(newPassword)){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("không được trùng với mật khẩu cũ");
            return false;
        }else {
            tvError.setVisibility(View.GONE);
        }

        if (!reNewPassword.equals(newPassword)){
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("xác nhận đúng mật khẩu mới");
            return false;
        }else {
            tvError.setVisibility(View.GONE);
        }

        return true;
    }
}