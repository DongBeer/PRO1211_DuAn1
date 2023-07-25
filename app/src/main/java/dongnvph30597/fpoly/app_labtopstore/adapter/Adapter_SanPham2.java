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

import dongnvph30597.fpoly.app_labtopstore.DAO.GioHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_SanPham2 extends RecyclerView.Adapter<Adapter_SanPham2.SP2ViewHolder>{

    private ImageView imgBackCTSP, imgSPCT;
    private TextView tvtenSPCT, tvMotaSPCT, tvTHSPCT, tvGiaSPCT;


    private Context context;
    private ArrayList<SanPham> arr = new ArrayList<>();

    private GioHangDAO gioHangDAO;
    private UserDAO userDAO;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");


    public Adapter_SanPham2(Context context, ArrayList<SanPham> arr) {
        this.context = context;
        this.arr = arr;
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

                SanPham sp = arr.get(index);
                Glide.with(context).load(sp.getImgSP()).into(imgSPCT);
                tvtenSPCT.setText(sp.getTenSP());
                tvMotaSPCT.setText(sp.getMoTa());
                ThuongHieuDao thuongHieuDao = new ThuongHieuDao(context);
                ThuongHieu th = thuongHieuDao.getID(String.valueOf(sp.getMaTH()));
                tvTHSPCT.setText(th.getTenTH());
                String formattedPrice1 = decimalFormat.format(sp.getGiaSP());
                tvGiaSPCT.setText(formattedPrice1 + " ₫");
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
