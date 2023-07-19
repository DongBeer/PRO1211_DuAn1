package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dongnvph30597.fpoly.app_labtopstore.R;


public class UserFragment_SanPham extends Fragment {


    public UserFragment_SanPham() {
        // Required empty public constructor
    }

    public static UserFragment_SanPham newInstance(String param1, String param2) {
        UserFragment_SanPham fragment = new UserFragment_SanPham();
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
        return inflater.inflate(R.layout.fragment_user__sanpham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}