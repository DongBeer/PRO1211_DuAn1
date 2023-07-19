package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.Login_Activity;


public class Fragment_DangXuat extends Fragment {


    public Fragment_DangXuat() {
        // Required empty public constructor
    }

    public static Fragment_DangXuat newInstance(String param1, String param2) {
        Fragment_DangXuat fragment = new Fragment_DangXuat();
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
        return inflater.inflate(R.layout.fragment__dangxuat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startActivity(new Intent(getActivity(), Login_Activity.class));
        getActivity().finish();
    }
}