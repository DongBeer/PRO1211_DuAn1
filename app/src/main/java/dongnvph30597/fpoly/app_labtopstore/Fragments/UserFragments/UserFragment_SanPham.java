package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThongKeDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.GioHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_KhachHang;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_SanPham2;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_Slide_LoaiSP;
import dongnvph30597.fpoly.app_labtopstore.adapter.SlideAdapter;
import dongnvph30597.fpoly.app_labtopstore.model.ImageSlide;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;
import me.relex.circleindicator.CircleIndicator3;


public class UserFragment_SanPham extends Fragment {

    private RecyclerView recyclerSPKh, recyclerSlideTH;
    private ImageView imgGotoCart;
    private EditText edSearchSP;
    private TextView tvSearch, tvTotalSP;
    private ThongKeDAO thongKeDAO;

    private SanPhamDAO sanPhamDAO;
    private Adapter_SanPham2 adapter;
    private ArrayList<SanPham> arr = new ArrayList<>();

    private ThuongHieuDao thuongHieuDao;
    private Adapter_Slide_LoaiSP adapterSlide;
    private ArrayList<ThuongHieu> arrImg = new ArrayList<>();

    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator;
    private List<ImageSlide> list;
    private SlideAdapter slideAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (viewPager2.getCurrentItem() == list.size() - 1) {
                viewPager2.setCurrentItem(0);
            }else {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
            }
        }
    };

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
        recyclerSlideTH = view.findViewById(R.id.recyclerimgTH);
        imgGotoCart = view.findViewById(R.id.gotoCart);
        edSearchSP = view.findViewById(R.id.edSearchSP);
        tvSearch = view.findViewById(R.id.tvSearch);
        tvTotalSP = view.findViewById(R.id.tvtotalsp);

        viewPager2 = view.findViewById(R.id.slide_banner);
        circleIndicator = view.findViewById(R.id.indicator);

        list = getListImage();
        slideAdapter = new SlideAdapter(getContext());
        slideAdapter.setList(list);
        viewPager2.setAdapter(slideAdapter);
        circleIndicator.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });

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
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        LoadDataGridlayout(recyclerSPKh, sanPhamDAO.getAllSP());
        LoadImgTH();
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

    public void LoadImgTH(){
        thuongHieuDao = new ThuongHieuDao(getContext());
        arrImg = thuongHieuDao.selectAll();
        adapterSlide = new Adapter_Slide_LoaiSP(getContext(), arrImg, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ThuongHieu th = arrImg.get(position);
                int maTH = th.getMaTH();
                LoadDataGridlayout(recyclerSPKh,sanPhamDAO.getSanPhamByMaTH(maTH));
            }
        });
        adapterSlide.setData(arrImg);
        recyclerSlideTH.setAdapter(adapterSlide);
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadDataGridlayout(recyclerSPKh,sanPhamDAO.getAllSP());
    }

    private List<ImageSlide> getListImage() {
        List<ImageSlide> list = new ArrayList<>();
        list.add(new ImageSlide(R.drawable.slide5));
        list.add(new ImageSlide(R.drawable.anhhome1));
        list.add(new ImageSlide(R.drawable.slide2));
        list.add(new ImageSlide(R.drawable.anhhome2));
        list.add(new ImageSlide(R.drawable.slide3));
        list.add(new ImageSlide(R.drawable.anhhome3));
        list.add(new ImageSlide(R.drawable.slide4));





        return list;
    }
}