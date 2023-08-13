package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dongnvph30597.fpoly.app_labtopstore.DAO.ThongKeDAO;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;


public class Fragment_ThongKe extends Fragment {

    private BarChart barChart;
    private ImageView imgTungay, imgDenngay, imgHomeDT;
    private EditText edTungay, edDenngay;
    private TextView tvDoanhThu;
    private Calendar myCalendar = Calendar.getInstance();
    private ThongKeDAO thongKeDAO;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
    private Button btnThongke;

    public Fragment_ThongKe() {
        // Required empty public constructor
    }


    public static Fragment_ThongKe newInstance() {
        Fragment_ThongKe fragment = new Fragment_ThongKe();

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
        return inflater.inflate(R.layout.fragment_thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgHomeDT = view.findViewById(R.id.imgHome_DT);
        imgHomeDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        barChart = view.findViewById(R.id.barChart);

        imgTungay = view.findViewById(R.id.imgTungay);
        imgDenngay = view.findViewById(R.id.imgDenngay);
        edTungay = view.findViewById(R.id.edTungay);
        edDenngay = view.findViewById(R.id.edDenngay);
        tvDoanhThu = view.findViewById(R.id.tvDoanhthu);
        btnThongke = view.findViewById(R.id.btnDoanhThu);
        edTungay.setFocusable(false);
        edDenngay.setFocusable(false);
        thongKeDAO = new ThongKeDAO(getContext());


        imgTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogDT(edTungay);
            }
        });

        imgDenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogDT(edDenngay);

            }
        });
        btnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edTungay.getText().toString().trim();
                String denngay = edDenngay.getText().toString().trim();
                if (tungay.isEmpty() || denngay.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ từ ngày và đến ngày!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String[] temptungay = tungay.split("/");
                    String[] tempdenngay = denngay.split("/");

                    int inttungay = Integer.parseInt(temptungay[0] + temptungay[1] + temptungay[2]);
                    int intdenngay = Integer.parseInt(tempdenngay[0] + tempdenngay[1] + tempdenngay[2]);

                    if (inttungay > intdenngay) {
                        Toast.makeText(getContext(), "Lỗi! Từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    thongKeDAO = new ThongKeDAO(getContext());
                    tvDoanhThu.setText(decimalFormat.format(thongKeDAO.getThongke(tungay, denngay)) + " ₫");
                }
            }
        });


        setDoanhThuToBarChart();


    }
    @SuppressLint("Range")
    private void setDoanhThuToBarChart() {
        // Lấy doanh thu từ cơ sở dữ liệu cho từng tháng

        int doanhthuT8 = thongKeDAO.getDoanhThuT8();
        int doanhthuT9 = thongKeDAO.getDoanhThuT9();
        int doanhthuT10 = thongKeDAO.getDoanhThuT10();
        int doanhthuT11 = thongKeDAO.getDoanhThuT11();
        int doanhthuT12 = thongKeDAO.getDoanhThuT12();
        // Tạo dữ liệu cột
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 0)); // Tháng 1
        entries.add(new BarEntry(2, 0)); // Tháng 2
        entries.add(new BarEntry(3, 0)); // Tháng 3
        entries.add(new BarEntry(4, 0)); // Tháng 4
        entries.add(new BarEntry(5, 0)); // Tháng 5
        entries.add(new BarEntry(6, 0)); // Tháng 6
        entries.add(new BarEntry(7, 0)); // Tháng 7
        entries.add(new BarEntry(8, doanhthuT8)); // Tháng 8
        entries.add(new BarEntry(9, doanhthuT9)); // Tháng 9
        entries.add(new BarEntry(10, doanhthuT10)); // Tháng 10
        entries.add(new BarEntry(11, doanhthuT11)); // Tháng 11
        entries.add(new BarEntry(12, doanhthuT12)); // Tháng 12

        // Tạo dữ liệu của BarDataSet và cấu hình
        BarDataSet dataSet = new BarDataSet(entries, "Doanh thu");
        dataSet.setColor(Color.parseColor("#00BCD4"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        dataSet.setBarBorderWidth(0.5f);

        // Tạo dữ liệu của BarData
        BarData barData = new BarData(dataSet);

        // Đặt dữ liệu vào BarChart
        barChart.setData(barData);

        // Cấu hình mô tả biểu đồ
        Description description = new Description();
        description.setText("Biểu đồ doanh thu hàng tháng");
        barChart.setDescription(description);

        // Cấu hình trục x và trục y
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getXAxis().setCenterAxisLabels(true);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setAxisMaximum(12);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setAxisMinimum(0);
        // Đặt giá trị tối đa cho trục y là giá trị lớn nhất trong dữ liệu doanh thu của các tháng
        int maxYValue =  Math.max(doanhthuT8,
                        Math.max(doanhthuT9, Math.max(doanhthuT10, Math.max(doanhthuT11, doanhthuT12))));
        barChart.getAxisLeft().setAxisMaximum(maxYValue + 200); // Cộng thêm 200 để tránh việc các cột quá gần nhau

        // Cập nhật biểu đồ
        barChart.invalidate();
    }



    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("Thg 1");
        xAxisValues.add("Thg 2");
        xAxisValues.add("Thg 3");
        xAxisValues.add("Thg 4");
        xAxisValues.add("Thg 5");
        xAxisValues.add("Thg 6");
        xAxisValues.add("Thg 7");
        xAxisValues.add("Thg 8");
        xAxisValues.add("Thg 9");
        xAxisValues.add("Thg 10");
        xAxisValues.add("Thg 11");
        xAxisValues.add("Thg 12");
        return xAxisValues;
    }

    private void showDatePickerDialogDT(final EditText editText) {
        int year = myCalendar.get(Calendar.YEAR);
        int month = myCalendar.get(Calendar.MONTH);
        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        myCalendar.set(Calendar.YEAR, i);
                        myCalendar.set(Calendar.MONTH, i1);
                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        Date selectedDate = myCalendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        String time = (dateFormat.format(selectedDate));
                        editText.setText(time);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    }

