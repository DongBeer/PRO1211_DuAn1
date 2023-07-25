package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThongKeDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.MainActivity;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_SanPham;
import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_SpinerTH;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;


public class Fragment_QuanlySanPham extends Fragment {

    private ImageView imgmenuSP;
    private FloatingActionButton floatAddSP;
    private RecyclerView recyclerviewSP;

    private SanPhamDAO sanPhamDAO;
    private Adapter_SanPham adapter;
    private ArrayList<SanPham> arr = new ArrayList<>();
    private TextView tvTongSP;
    private ThongKeDAO thongKeDAO;

    private ThuongHieuDao thuongHieuDao;
    private ArrayList<ThuongHieu> arrTH = new ArrayList<>();
    private Adapter_SpinerTH adapterSpinerTH;
    private String maTH;

    private ImageView addimgSP;
    private TextInputEditText edTensp, edMotasp, edgiaSP, edLoaisp, edSoluongSP;
    private Spinner spnTH;
    private TextView tvAddSp, tvCanclerAddSp;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;
    private String imgSpPath;

    public Fragment_QuanlySanPham() {
        // Required empty public constructor
    }

    public static Fragment_QuanlySanPham newInstance() {
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
        imgmenuSP = view.findViewById(R.id.icon_menuSP);
        imgmenuSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        recyclerviewSP = view.findViewById(R.id.recyclerListSP);


        floatAddSP = view.findViewById(R.id.floatAddSP);
        floatAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAddsp();
            }
        });

        FilltoRecyclerSP();

        tvTongSP =view.findViewById(R.id.tvTongsanpham);
        thongKeDAO = new ThongKeDAO(getContext());
        int total = thongKeDAO.getTotalProduct();
        tvTongSP.setText("Laptop(" + total + " sản phẩm)" );

    }

    public void FilltoRecyclerSP(){
        sanPhamDAO = new SanPhamDAO(getContext());
        adapter = new Adapter_SanPham(getContext(), sanPhamDAO, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog mDialog = new Dialog(getContext());
                mDialog.setContentView(R.layout.layout_dialog_add_sanpham);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                addimgSP = mDialog.findViewById(R.id.AddimgSP);
                edTensp = mDialog.findViewById(R.id.edTenSP);
                edMotasp = mDialog.findViewById(R.id.edMotaSP);
                spnTH = mDialog.findViewById(R.id.spnTH);
                edgiaSP = mDialog.findViewById(R.id.edGiaSP);
                edLoaisp = mDialog.findViewById(R.id.edLoaiSP);
                edSoluongSP = mDialog.findViewById(R.id.edSoluongSP);
                tvAddSp = mDialog.findViewById(R.id.tvAddSP);
                tvCanclerAddSp = mDialog.findViewById(R.id.tvCanclerAdd);

                tvAddSp.setText("    Sửa → ");
                tvCanclerAddSp.setText("   - Xóa  ");

                spnTH();
                addimgclick();

                for (int i = 0; i < arrTH.size(); i++) {
                    if (arr.get(position).getMaTH() == arrTH.get(i).getMaTH()) {
                        spnTH.setSelection(i);
                        break;
                    }
                }

                SanPham sp = arr.get(position);
                edTensp.setText(sp.getTenSP());
                edMotasp.setText(sp.getMoTa());
                edgiaSP.setText(String.valueOf(sp.getGiaSP()));
                edLoaisp.setText(sp.getLoaiSP());
                edSoluongSP.setText(String.valueOf(sp.getSoLuong()));
                Glide.with(getContext())
                        .load(sp.getImgSP())
                        .into(addimgSP);

                tvAddSp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tensp = edTensp.getText().toString().trim();
                        String motasp = edMotasp.getText().toString().trim();
                        String giasp = edgiaSP.getText().toString().trim();
                        String loaisp = edLoaisp.getText().toString().trim();
                        String slsp = edSoluongSP.getText().toString().trim();
                        int id = arr.get(position).getMaSP();

                        if (validateInput(tensp, "Vui lòng nhập tên sp", edTensp)) {
                            return;
                        }

                        if (validateInput(motasp, "Vui lòng nhập tên đăng nhập", edMotasp)) {
                            return;
                        }

                        if (validateInput(giasp, "Vui lòng nhập mật khẩu", edgiaSP)) {
                            return;
                        }
                        if (validateInput(loaisp, "Vui lòng nhập số điện thoại", edLoaisp)) {
                            return;
                        }

                        if (validateInput(slsp, "Vui lòng nhập địa chỉ", edSoluongSP)) {
                            return;
                        }
                        if(imgSpPath == null){
                            Toast.makeText(getContext(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SanPham sanPham = new SanPham();
                        sanPham.setMaSP(id);
                        sanPham.setImgSP(imgSpPath);
                        sanPham.setTenSP(tensp);
                        sanPham.setMoTa(motasp);
                        sanPham.setMaTH(Integer.valueOf(maTH));
                        sanPham.setGiaSP(Integer.valueOf(giasp));
                        sanPham.setLoaiSP(loaisp);
                        sanPham.setSoLuong(Integer.valueOf(slsp));
                        sanPhamDAO.update(sanPham);
                        FilltoRecyclerSP();
                        Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();

                    }
                });

                tvCanclerAddSp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Bạn có muốn xóa sản phẩm" + arr.get(position).getTenSP());
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = arr.get(position).getMaSP();
                                sanPhamDAO.delete(String.valueOf(id));
                                FilltoRecyclerSP();
                                Toast.makeText(getContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                mDialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("không",null);

                        builder.show();
                    }
                });
                mDialog.show();

            }
        });
        arr = sanPhamDAO.getAllSP();
        adapter.setData(arr);
        recyclerviewSP.setAdapter(adapter);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgSpPath = getRealPathFromUri(selectedImage);
            Glide.with(getContext())
                    .load(selectedImage)
                    .into(addimgSP);
        }
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, mở thư viện ảnh
                openImagePicker();
            } else {
                // Quyền bị từ chối, hiển thị thông báo hoặc thực hiện xử lý khác
                Toast.makeText(getContext(), "Permission denied. Unable to access external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        FilltoRecyclerSP();
    }

    private boolean validateInput(String input, String errorMessage, TextInputEditText editText) {
        if (input.isEmpty()) {
            editText.setError(errorMessage);
            editText.requestFocus();
            return true;
        }
        return false;
    }
    public void checkAddsp(){
        thuongHieuDao = new ThuongHieuDao(getContext());
        arrTH = thuongHieuDao.selectAll();
        if(arrTH.size() == 0){
            Toast.makeText(getContext(), "Bạn chưa thêm 'Loại sản phẩm' ", Toast.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_add_sanpham);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        addimgSP = dialog.findViewById(R.id.AddimgSP);
        edTensp = dialog.findViewById(R.id.edTenSP);
        edMotasp = dialog.findViewById(R.id.edMotaSP);
        spnTH = dialog.findViewById(R.id.spnTH);
        edgiaSP = dialog.findViewById(R.id.edGiaSP);
        edLoaisp = dialog.findViewById(R.id.edLoaiSP);
        edSoluongSP = dialog.findViewById(R.id.edSoluongSP);
        tvAddSp = dialog.findViewById(R.id.tvAddSP);
        tvCanclerAddSp = dialog.findViewById(R.id.tvCanclerAdd);

        tvCanclerAddSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        spnTH();

        addimgclick();

        tvAddSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensp = edTensp.getText().toString().trim();
                String motasp = edMotasp.getText().toString().trim();
                String giasp = edgiaSP.getText().toString().trim();
                String loaisp = edLoaisp.getText().toString().trim();
                String slsp = edSoluongSP.getText().toString().trim();

                if (validateInput(tensp, "Vui lòng nhập tên sp", edTensp)) {
                    return;
                }

                if (validateInput(motasp, "Vui lòng nhập tên đăng nhập", edMotasp)) {
                    return;
                }

                if (validateInput(giasp, "Vui lòng nhập mật khẩu", edgiaSP)) {
                    return;
                }
                if (validateInput(loaisp, "Vui lòng nhập số điện thoại", edLoaisp)) {
                    return;
                }

                if (validateInput(slsp, "Vui lòng nhập địa chỉ", edSoluongSP)) {
                    return;
                }
                if(imgSpPath == null){
                    Toast.makeText(getContext(), "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SanPham sanPham = new SanPham();
                sanPham.setImgSP(imgSpPath);
                sanPham.setTenSP(tensp);
                sanPham.setMoTa(motasp);
                sanPham.setMaTH(Integer.valueOf(maTH));
                sanPham.setGiaSP(Integer.valueOf(giasp));
                sanPham.setLoaiSP(loaisp);
                sanPham.setSoLuong(Integer.valueOf(slsp));

                sanPhamDAO.insert(sanPham);
                FilltoRecyclerSP();
                Toast.makeText(getContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void spnTH(){
        thuongHieuDao = new ThuongHieuDao(getContext());
        arrTH = thuongHieuDao.selectAll();
        adapterSpinerTH = new Adapter_SpinerTH(getContext(),arrTH);
        spnTH.setAdapter(adapterSpinerTH);


        spnTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTH = String.valueOf(arrTH.get(position).getMaTH());
                //gọi từ private xuống
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void addimgclick(){
        addimgSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Nếu quyền chưa được cấp, yêu cầu quyền
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                } else {
                    // Nếu quyền đã được cấp, mở thư viện ảnh
                    openImagePicker();
                }
            }
        });
    }

}