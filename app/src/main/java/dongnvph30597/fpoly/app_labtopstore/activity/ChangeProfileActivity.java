package dongnvph30597.fpoly.app_labtopstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class ChangeProfileActivity extends AppCompatActivity {

    private ImageView icBack, imgAvatar;
    private EditText edtFullName, edtUsername, edtAddress, edtPhoneNumber;
    private Button btnCancel, btnSave;
    private UserDAO dao;
    private User user;
    private String imagePath;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);
        initViews();
    }

    private void initViews(){
        imgAvatar = findViewById(R.id.circleImageView);
        icBack  =findViewById(R.id.ic_back);
        edtFullName = findViewById(R.id.edt_fullname);
        edtUsername = findViewById(R.id.edt_username);
        edtPhoneNumber = findViewById(R.id.edt_phone_number);
        edtAddress = findViewById(R.id.edt_address);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);
        dao = new UserDAO(this);
        Log.d( "ChangeProfileActivity ",UserDAO.idUser+"");
        user = dao.getUserById(UserDAO.idUser);
        Log.d("BBB", user.toString());

        icBack.setOnClickListener(v->finish());
        edtFullName.setText(user.getHoTen());
        edtUsername.setText(user.getTenDangnhap());
        edtPhoneNumber.setText(user.getSoDT());
        edtAddress.setText(user.getDiaChi());
        Glide.with(this).load(user.getImgUser()).error(R.drawable.signup).into(imgAvatar);

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ChangeProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Nếu quyền chưa được cấp, yêu cầu quyền
                    ActivityCompat.requestPermissions(ChangeProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                } else {
                    // Nếu quyền đã được cấp, mở thư viện ảnh
                    openImagePicker();
                }
            }
        });

        btnCancel.setOnClickListener(v->clearText());

        btnSave.setOnClickListener(v->{
            String fullName = edtFullName.getText().toString();
            String username = edtUsername.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String address = edtAddress.getText().toString();
            //todo: sửa ở đây
            if (imagePath == null){
                imagePath = user.getImgUser();
            }
            //-----------------------------
            user.setImgUser(imagePath);
            user.setTenDangnhap(username);
            user.setHoTen(fullName);
            user.setSoDT(phoneNumber);
            user.setDiaChi(address);
            Log.d("BBB", user.toString());
            if(imagePath == null){
                Toast.makeText(this, ""+ imagePath, Toast.LENGTH_SHORT).show();
                return;
            }
            if(dao.update(user) > 0){
                Toast.makeText(this, "update user information successfully", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this, "false " , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearText() {
        edtFullName.setText("");
        edtUsername.setText("");
        edtPhoneNumber.setText("");
        edtAddress.setText("");
        Glide.with(this).load(user.getImgUser()).error(R.drawable.signup).into(imgAvatar);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imagePath = getRealPathFromUri(selectedImage);
            Glide.with(ChangeProfileActivity.this)
                    .load(selectedImage)
                    .into(imgAvatar);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ChangeProfileActivity.this.getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, mở thư viện ảnh
                openImagePicker();
            } else {
                // Quyền bị từ chối, hiển thị thông báo hoặc thực hiện xử lý khác
                Toast.makeText(ChangeProfileActivity.this, "Permission denied. Unable to access external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}