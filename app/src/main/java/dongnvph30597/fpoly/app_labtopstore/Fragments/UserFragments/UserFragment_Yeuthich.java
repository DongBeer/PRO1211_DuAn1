package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_SanPham2;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;

public class UserFragment_Yeuthich extends Fragment implements Adapter_SanPham2.ChangeTrangThai{

    private RecyclerView recyclerListSPYT;

    private Adapter_SanPham2 adapter;
    private ArrayList<SanPham> arr = new ArrayList<>();
    private SanPhamDAO sanPhamDAO;

    public UserFragment_Yeuthich() {
        // Required empty public constructor
    }


    public static UserFragment_Yeuthich newInstance() {
        UserFragment_Yeuthich fragment = new UserFragment_Yeuthich();
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
        return inflater.inflate(R.layout.fragment_user__yeuthich, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerListSPYT = view.findViewById(R.id.recyclerListSPYeuthich);
        FilltoListSPYT();
        adapter.setTrangThaiListener(this);
        if(arr.size() == 0){
            Toast.makeText(getContext(), "Bạn chưa thêm sản phẩm nào trong mục yêu thích!", Toast.LENGTH_SHORT).show();
        }
    }

    public void FilltoListSPYT(){
        sanPhamDAO = new SanPhamDAO(getContext());
        arr = sanPhamDAO.getSPYeuthich();
        adapter = new Adapter_SanPham2(getContext(),arr);
        adapter.setData(arr);
        recyclerListSPYT.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(arr.size() == 0){
            Toast.makeText(getContext(), "Bạn chưa thêm sản phẩm nào trong mục yêu thích!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void trangThaiChanged(int position, int newTrangThai) {
        FilltoListSPYT();
    }
}