package dongnvph30597.fpoly.app_labtopstore.Fragments;

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


        // Tạo dữ liệu cột
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 20f));
        entries.add(new BarEntry(2, 35f));
        entries.add(new BarEntry(3, 10f));
        entries.add(new BarEntry(4, 45f));
        entries.add(new BarEntry(5, 30f));

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
        barChart.getAxisLeft().setAxisMaximum(1000);

        // Cập nhật biểu đồ
        barChart.invalidate();
    }

    // Giá trị trục x
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("Tháng 1");
        xAxisValues.add("Tháng 2");
        xAxisValues.add("Tháng 3");
        xAxisValues.add("Tháng 4");
        xAxisValues.add("Tháng 5");
        xAxisValues.add("Tháng 6");
        xAxisValues.add("Tháng 7");
        xAxisValues.add("Tháng 8");
        xAxisValues.add("Tháng 9");
        xAxisValues.add("Tháng 10");
        xAxisValues.add("Tháng 11");
        xAxisValues.add("Tháng 12");
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

