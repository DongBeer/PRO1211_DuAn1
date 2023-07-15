package dongnvph30597.fpoly.app_labtopstore.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.adapter.LoaiSanPhamAdapter;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;


public class Fragment_QuanLyLoaiSp extends Fragment {

    private FloatingActionButton fabAdd;
    private Toolbar toolbar;
    private RecyclerView rcv;
    private List<ThuongHieu> list;
    private LoaiSanPhamAdapter adapter;

    private ThuongHieuDao dao;
    ImageView img;
    private String imagePath;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    public Fragment_QuanLyLoaiSp() {
        // Required empty public constructor
    }

    public static Fragment_QuanLyLoaiSp newInstance() {
        Fragment_QuanLyLoaiSp fragment = new Fragment_QuanLyLoaiSp();
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
        return inflater.inflate(R.layout.fragment_quan_ly_loai_sp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabAdd = view.findViewById(R.id.fab_add);
        rcv = view.findViewById(R.id.rcv_loaisp);
        dao = new ThuongHieuDao(getContext());
        list = new ArrayList<>();
        list = dao.selectAll();
        adapter = new LoaiSanPhamAdapter(getContext(),dao);
        adapter.setData(list);
        rcv.setAdapter(adapter);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_add_loaisp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText edtName = dialog.findViewById(R.id.edit_name);
                img = dialog.findViewById(R.id.btn_add_image);
                CardView btnAdd = dialog.findViewById(R.id.btn_add);

                ThuongHieu obj = new ThuongHieu();
                img.setOnClickListener(new View.OnClickListener() {
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
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString();
                        if (!name.isEmpty() && imagePath != null) {
                            obj.setTenTH(name);
                            obj.setImgTH(imagePath);

                            dao.insert(obj);
                            list = dao.selectAll();
                            adapter.setData(list);
                            dialog.dismiss();
                        } else {
                            // Handle case when either name or imagePath is empty
                            Toast.makeText(getContext(), "Please enter name and select an image", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.show();
            }
        });

        //sửa
        adapter.setOnItemClickSelected(new LoaiSanPhamAdapter.onItemClickSelected() {
            @Override
            public void onItemClick(int position) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.layout_add_loaisp);
                Window window = dialog.getWindow();
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                EditText edtName = dialog.findViewById(R.id.edit_name);
                img = dialog.findViewById(R.id.btn_add_image);
                CardView btnAdd = dialog.findViewById(R.id.btn_add);

                ThuongHieu obj = list.get(position);
                img.setOnClickListener(new View.OnClickListener() {
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

                imagePath = obj.getImgTH();
                edtName.setText(obj.getTenTH());
                Glide.with(getContext())
                        .load(obj.getImgTH())
                        .into(img);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = edtName.getText().toString();
                        obj.setTenTH(name);
                        obj.setImgTH(imagePath);
                        dao.update(obj);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });
        //sửa

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imagePath = getRealPathFromUri(selectedImage);
            Glide.with(getContext())
                    .load(selectedImage)
                    .into(img);
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

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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
        list= dao.selectAll();
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}