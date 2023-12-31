package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.ChangePasswordActivity;
import dongnvph30597.fpoly.app_labtopstore.activity.ChangeProfileActivity;
import dongnvph30597.fpoly.app_labtopstore.activity.KhachHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.activity.Login_Activity;
import dongnvph30597.fpoly.app_labtopstore.activity.UserActivity_TrangThaiDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.SharedPreferencesHelper;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class UserFragment_Thongtin extends Fragment {

    private LinearLayout btnChangePassword, btnChangeProfile, btnTTDH, lnbtnDathich;
    private Button btnLogout;
    private ImageView img;
    private TextView tvName;
    private UserDAO dao;
    private User user;
    private SharedPreferencesHelper preferencesHelper;

    public UserFragment_Thongtin() {
        // Required empty public constructor
    }


    public static UserFragment_Thongtin newInstance(String param1, String param2) {
        UserFragment_Thongtin fragment = new UserFragment_Thongtin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user__thongtin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferencesHelper = new SharedPreferencesHelper(getContext());
        btnLogout = view.findViewById(R.id.btn_logout);
        btnChangePassword = view.findViewById(R.id.layout_btn_change_password);
        btnChangeProfile = view.findViewById(R.id.btn_change_profile);
        img = view.findViewById(R.id.img_avatar);
        tvName = view.findViewById(R.id.tv_name_user);

        btnTTDH = view.findViewById(R.id.linear_btn_DHUser);
        dao = new UserDAO(getContext());
        user = dao.getUserById(UserDAO.idUser);


        if (user.getImgUser() != null ){
            Glide.with(this).load(user.getImgUser()).error(R.drawable.user_login).into(img);
        }
        tvName.setText(user.getHoTen());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Bạn muốn đang xuất?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();

                                preferencesHelper.clearCheckedItems();

                                Intent mIntent = new Intent(getContext(), Login_Activity.class);
                                mIntent.putExtra("RESET_LOGIN_STATE", true);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(mIntent);
                                getActivity().finishAffinity();
                            }
                        },1000);
                    }
                });
                builder.setNegativeButton("Không",null);

                builder.show();
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getActivity(), ChangePasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aIntent = new Intent(getActivity(), ChangeProfileActivity.class);
                aIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(aIntent);
            }
        });

        btnTTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getContext(), UserActivity_TrangThaiDonHang.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mIntent);
            }
        });

        lnbtnDathich = view.findViewById(R.id.lnbtnDathich);
        lnbtnDathich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager(); // Sử dụng getParentFragmentManager() trong Fragment.
                fragmentManager.beginTransaction()
                        .replace(R.id.user_container, new UserFragment_Yeuthich())
                        .addToBackStack(null) // Thêm Fragment hiện tại vào BackStack để có thể quay lại khi nhấn nút back.
                        .commit();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        dao = new UserDAO(getContext());
        user = dao.getUserById(UserDAO.idUser);

        if (user.getImgUser() != null ){
            Glide.with(this).load(user.getImgUser()).error(R.drawable.signup).into(img);
        }
        tvName.setText(user.getHoTen());
    }
}