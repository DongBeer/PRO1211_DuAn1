package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.ChangePasswordActivity;
import dongnvph30597.fpoly.app_labtopstore.activity.ChangeProfileActivity;
import dongnvph30597.fpoly.app_labtopstore.activity.Login_Activity;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class UserFragment_Thongtin extends Fragment {

    private LinearLayout btnChangePassword, btnChangeProfile;
    private Button btnLogout;
    private ImageView img;
    private TextView tvName;
    private UserDAO dao;
    private User user;

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

        btnLogout = view.findViewById(R.id.btn_logout);
        btnChangePassword = view.findViewById(R.id.layout_btn_change_password);
        btnChangeProfile = view.findViewById(R.id.btn_change_profile);
        img = view.findViewById(R.id.img_avatar);
        tvName = view.findViewById(R.id.tv_name_user);

        dao = new UserDAO(getContext());
        user = dao.getUserById(UserDAO.idUser);

        Glide.with(this).load(user.getImgUser()).error(R.drawable.signup).into(img);
        tvName.setText(user.getHoTen());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAffinity();
                startActivity(new Intent(getActivity(), Login_Activity.class));
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangeProfileActivity.class));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}