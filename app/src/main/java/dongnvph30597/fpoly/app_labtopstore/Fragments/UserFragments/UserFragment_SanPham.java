package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThongKeDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.GioHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_KhachHang;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_SanPham2;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;


public class UserFragment_SanPham extends Fragment {

    private RecyclerView recyclerSPKh;
    private ImageView imgGotoCart;
    private EditText edSearchSP;
    private TextView tvSearch, tvTotalSP;
    private ThongKeDAO thongKeDAO;

    private SanPhamDAO sanPhamDAO;
    private Adapter_SanPham2 adapter;
    private ArrayList<SanPham> arr = new ArrayList<>();
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

        recyclerSPKh = view.findViewById(R.id.recyclerSPKH);
        imgGotoCart = view.findViewById(R.id.gotoCart);
        edSearchSP = view.findViewById(R.id.edSearchSP);
        tvSearch = view.findViewById(R.id.tvSearch);
        tvTotalSP = view.findViewById(R.id.tvtotalsp);
        thongKeDAO = new ThongKeDAO(getContext());
        int total = thongKeDAO.getTotalProduct();
        tvTotalSP.setText("("+total+" sản phẩm)");


        sanPhamDAO = new SanPhamDAO(getContext());
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edSearchSP.getText().toString();
                LoadDataGridlayout(recyclerSPKh,sanPhamDAO.searchSanPham(search));
            }
        });



        imgGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GioHang_Activity.class);
                startActivity(intent);
            }
        });

        LoadDataGridlayout(recyclerSPKh, sanPhamDAO.getAllSP());
    }

    public void LoadDataGridlayout(RecyclerView recyclerView, ArrayList arrayList){
        int colums = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),colums));
        sanPhamDAO = new SanPhamDAO(getContext());
        arr = arrayList;
        adapter = new Adapter_SanPham2(getContext(),arr);
        adapter.setData(arr);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadDataGridlayout(recyclerSPKh,sanPhamDAO.getAllSP());
    }
}