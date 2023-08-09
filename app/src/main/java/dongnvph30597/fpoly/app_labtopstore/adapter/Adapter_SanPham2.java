package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DanhGiaDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.GioHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.DatHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.activity.GioHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.model.DanhGia;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_SanPham2 extends RecyclerView.Adapter<Adapter_SanPham2.SP2ViewHolder>{

    private ImageView imgBackCTSP, imgSPCT, imgtrangThaiSP;
    private TextView tvtenSPCT, tvMotaSPCT, tvTHSPCT, tvGiaSPCT, tvTBCdanhgia, tvsldaban;
    private EditText edAddbl;
    private Button btnGui;
    private int trangThai = 0;
    private ArrayList<DanhGia> arrDG = new ArrayList<>();
    private LinearLayout lnbtnThemvaoGH, lnbtnMuangay;

    private Context context;
    private ArrayList<SanPham> arr = new ArrayList<>();

    private GioHangDAO gioHangDAO;
    private UserDAO userDAO;
    private SanPhamDAO sanPhamDAO;
    private DanhGiaDAO danhGiaDAO;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");


    public interface ChangeTrangThai {
        void trangThaiChanged(int position, int newTrangThai);
    }

    private ChangeTrangThai trangThaiListener;

    public Adapter_SanPham2(Context context, ArrayList<SanPham> arr) {
        this.context = context;
        this.arr = arr;
    }

    public void setTrangThaiListener(ChangeTrangThai listener) {
        this.trangThaiListener = listener;
    }


    public void setData(ArrayList<SanPham> arrSP){
        this.arr = arrSP;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SP2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sanpham_kh,parent,false);
        final SP2ViewHolder holder = new SP2ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                Dialog mDialog = new Dialog(context);
                mDialog.setContentView(R.layout.layout_dialog_chitietsanpham);
                mDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                imgBackCTSP = mDialog.findViewById(R.id.imgbackCTSP);
                imgBackCTSP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                imgSPCT = mDialog.findViewById(R.id.imgSPCT);
                tvtenSPCT = mDialog.findViewById(R.id.tvTenspCT);
                tvMotaSPCT = mDialog.findViewById(R.id.tvMotaspCT);
                tvTHSPCT = mDialog.findViewById(R.id.tvTHspCT);
                tvGiaSPCT = mDialog.findViewById(R.id.tvGiaspCT);
                imgtrangThaiSP = mDialog.findViewById(R.id.imgtrangthaisp);
                tvTBCdanhgia = mDialog.findViewById(R.id.tvTBCdanhgia);
                tvsldaban = mDialog.findViewById(R.id.tvsldaban);
                edAddbl = mDialog.findViewById(R.id.edaddBL);
                btnGui = mDialog.findViewById(R.id.btnGui);
                lnbtnThemvaoGH = mDialog.findViewById(R.id.lnbtnthemvaogiohang);
                lnbtnMuangay = mDialog.findViewById(R.id.lnbtnmuangay);

                SanPham sp = arr.get(index);
                Glide.with(context).load(sp.getImgSP()).into(imgSPCT);
                tvtenSPCT.setText(sp.getTenSP());
                tvMotaSPCT.setText(sp.getMoTa());
                ThuongHieuDao thuongHieuDao = new ThuongHieuDao(context);
                ThuongHieu th = thuongHieuDao.getID(String.valueOf(sp.getMaTH()));
                tvTHSPCT.setText(th.getTenTH());
                String formattedPrice1 = decimalFormat.format(sp.getGiaSP());
                tvGiaSPCT.setText(formattedPrice1 + " ₫");
                danhGiaDAO = new DanhGiaDAO(context);
                tvTBCdanhgia.setText(danhGiaDAO.getAverageDanhGiaByMaSP(sp.getMaSP())+"");
                if(danhGiaDAO.getAverageDanhGiaByMaSP(sp.getMaSP()) == 0){
                    tvTBCdanhgia.setText("5.0");
                }
                tvsldaban.setText(danhGiaDAO.getTongSoLuongDaBan(sp.getMaSP())+"");

                imgtrangThaiSP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (trangThai == 0) {
                            trangThai = 1;
                            Toast.makeText(context, "Đã thêm vào danh mục yêu thích ♥", Toast.LENGTH_SHORT).show();
                            imgtrangThaiSP.setImageResource(R.drawable.heart_icon1); // Nếu trạng thái = 1 (đã yêu thích)
                            sp.setTrangThai(trangThai);
                            sanPhamDAO = new SanPhamDAO(context);
                            sanPhamDAO.update(sp);
                            notifyDataSetChanged();

                        } else {
                            trangThai = 0;
                            Toast.makeText(context, "Đã bỏ yêu thích!", Toast.LENGTH_SHORT).show();
                            imgtrangThaiSP.setImageResource(R.drawable.heart_icon); // Nếu trạng thái = 0 (chưa yêu thích)
                            sp.setTrangThai(trangThai);
                            sanPhamDAO = new SanPhamDAO(context);
                            sanPhamDAO.update(sp);
                            notifyDataSetChanged();

                        }
                        if (trangThaiListener != null) {
                            trangThaiListener.trangThaiChanged(index, trangThai); // Thông báo về sự thay đổi trạng thái
                        }
                    }

                });
                // Nếu sản phẩm đã được yêu thích, set trạng thái và hình ảnh tương ứng
                if (sp.getTrangThai() == 1) {
                    trangThai = 1;
                    imgtrangThaiSP.setImageResource(R.drawable.heart_icon1);
                } else {
                    trangThai = 0;
                    imgtrangThaiSP.setImageResource(R.drawable.heart_icon);
                }

                RecyclerView recyclerDanhgia = mDialog.findViewById(R.id.recyclerDanhGia);
                danhGiaDAO = new DanhGiaDAO(context);
                arrDG = danhGiaDAO.getDanhGiaBymaSP1(sp.getMaSP());
                Adapter_DanhGia adapter = new Adapter_DanhGia(context,arrDG);
                recyclerDanhgia.setAdapter(adapter);


                btnGui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String bl = edAddbl.getText().toString().trim();
                        if(bl.isEmpty()){
                            Toast.makeText(context, "Hãy nhập bình luận...", Toast.LENGTH_SHORT).show();
                            edAddbl.requestFocus();
                            return;
                        }else {
                            SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            int maUser = preferences.getInt("maUser", -1);
                            DanhGia dg = new DanhGia();
                            dg.setNhanXet(bl);
                            dg.setMaSP(sp.getMaSP());
                            dg.setMaUser(maUser);

                            if(danhGiaDAO.insertBL(dg) > 0 ){
                                notifyDataSetChanged();
                                Toast.makeText(context, "Đã gửi bình luận!", Toast.LENGTH_SHORT).show();
                                edAddbl.setText("");
                                arrDG = danhGiaDAO.getDanhGiaBymaSP1(sp.getMaSP());
                                Adapter_DanhGia adapter = new Adapter_DanhGia(context,arrDG);
                                recyclerDanhgia.setAdapter(adapter);
                            }
                        }
                    }
                });

                lnbtnThemvaoGH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gioHangDAO = new GioHangDAO(context);

                        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        int maUser = preferences.getInt("maUser", -1);
                        int maSP = sp.getMaSP();
                        int soluong = 1;
                        int gia = sp.getGiaSP();
                        GioHang gioHang = new GioHang();
                        gioHang.setMaUser(maUser);
                        gioHang.setMaSP(maSP);
                        gioHang.setSoLuong(soluong);
                        gioHang.setGiaSP(gia);
                        gioHang.setTrangThai(0);
                        for (GioHang gioHang1 : gioHangDAO.getGioHangbyIdUser(maUser)) {
                            if (gioHang1.getMaSP() == maSP) {
                                Toast.makeText(context, "Bạn đã thêm sản phẩm bên giỏ hàng!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        if (gioHangDAO.insert(gioHang) > 0){
                            Toast.makeText(context, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(context, "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                lnbtnMuangay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gioHangDAO = new GioHangDAO(context);

                        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        int maUser = preferences.getInt("maUser", -1);
                        int maSP = sp.getMaSP();
                        int soluong = 1;
                        int gia = sp.getGiaSP();
                        GioHang gioHang = new GioHang();
                        gioHang.setMaUser(maUser);
                        gioHang.setMaSP(maSP);
                        gioHang.setSoLuong(soluong);
                        gioHang.setGiaSP(gia);
                        gioHang.setTrangThai(1);
                        gioHangDAO.deleteGioHangByTrangThaiAndMaUser(maUser);
                        if (gioHangDAO.insert(gioHang) > 0){
//                            Toast.makeText(context, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,DatHang_Activity.class);
                            intent.putExtra("tongtien",gia);
                            intent.putExtra("check", 5);
                            context.startActivity(intent);

                        }else {
                            Toast.makeText(context, "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mDialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SP2ViewHolder holder, int position) {
        SanPham sp = arr.get(position);
        Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(holder.imgKHSp);
        holder.tvKHtenSP.setText(sp.getTenSP());
        String formattedPrice = decimalFormat.format(sp.getGiaSP());
        holder.tvKHGiaSP.setText(formattedPrice);
        holder.imgAddtoCart.setImageResource(R.drawable.cart_icon);

        holder.imgAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sanPham = arr.get(holder.getAdapterPosition());
                gioHangDAO = new GioHangDAO(context);

                SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int maUser = preferences.getInt("maUser", -1);
                int maSP = sanPham.getMaSP();
                int soluong = 1;
                int gia = sanPham.getGiaSP();
                GioHang gioHang = new GioHang();
                gioHang.setMaUser(maUser);
                gioHang.setMaSP(maSP);
                gioHang.setSoLuong(soluong);
                gioHang.setGiaSP(gia);
                gioHang.setTrangThai(0);
                for (GioHang gioHang1 : gioHangDAO.getGioHangbyIdUser(maUser)) {
                    if (gioHang1.getMaSP() == maSP) {
                        Toast.makeText(context, "Bạn đã thêm sản phẩm bên giỏ hàng!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (gioHangDAO.insert(gioHang) > 0){
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(context, "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class SP2ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgKHSp, imgAddtoCart;
        private TextView tvKHtenSP, tvKHGiaSP;
        public SP2ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKHSp = itemView.findViewById(R.id.imgKHSP);
            imgAddtoCart = itemView.findViewById(R.id.imgCart);
            tvKHtenSP = itemView.findViewById(R.id.tvKHtenSP);
            tvKHGiaSP = itemView.findViewById(R.id.tvKHGiaSP);
        }
    }

}
