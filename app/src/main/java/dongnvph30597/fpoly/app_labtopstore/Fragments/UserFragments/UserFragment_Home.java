package dongnvph30597.fpoly.app_labtopstore.Fragments.UserFragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.SlideAdapter;
import dongnvph30597.fpoly.app_labtopstore.model.ImageSlide;
import dongnvph30597.fpoly.app_labtopstore.model.User;
import me.relex.circleindicator.CircleIndicator3;


public class UserFragment_Home extends Fragment {

    private ViewPager2 viewPager2;
    private TextView tvGreeting;
    private CircleIndicator3 circleIndicator;
    private List<ImageSlide> list;
    private SlideAdapter slideAdapter;
    private UserDAO dao;
    private User user;
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

    public UserFragment_Home() {
        // Required empty public constructor
    }


    public static UserFragment_Home newInstance(String param1, String param2) {
        UserFragment_Home fragment = new UserFragment_Home();
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
        return inflater.inflate(R.layout.fragment_user__home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.slide_banner);
        circleIndicator = view.findViewById(R.id.indicator);
        tvGreeting = view.findViewById(R.id.tv_greeting);

        dao = new UserDAO(getContext());
        user = dao.getUserById(UserDAO.idUser);

        tvGreeting.setText("Xin chào, "+ user.getHoTen());

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

    }

    private List<ImageSlide> getListImage() {
        List<ImageSlide> list = new ArrayList<>();

        list.add(new ImageSlide(R.drawable.anhhome1));
        list.add(new ImageSlide(R.drawable.anhhome2));
        list.add(new ImageSlide(R.drawable.anhhome3));




        return list;
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }
}