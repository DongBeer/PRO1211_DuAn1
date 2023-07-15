package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dongnvph30597.fpoly.app_labtopstore.R;


public class Fragment_QuanlySanPham extends Fragment {



    public Fragment_QuanlySanPham() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Fragment_QuanlySanPham newInstance(String param1, String param2) {
        Fragment_QuanlySanPham fragment = new Fragment_QuanlySanPham();
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
        return inflater.inflate(R.layout.fragment__quanly_sanpham, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}