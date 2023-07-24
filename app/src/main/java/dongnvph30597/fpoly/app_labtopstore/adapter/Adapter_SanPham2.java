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
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;

public class Adapter_SanPham2 extends RecyclerView.Adapter<Adapter_SanPham2.SP2ViewHolder>{

    private ImageView addimgSP;
    private TextInputEditText edTensp, edMotasp, edgiaSP, edLoaisp, edSoluongSP;
    private Spinner spnTH;
    private TextView tvAddSp, tvCanclerAddSp, tvTitle;
    private LinearLayout linearLayoutSoluong;

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
                mDialog.setContentView(R.layout.layout_dialog_add_sanpham);
                mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                linearLayoutSoluong = mDialog.findViewById(R.id.linearSoluong);
                tvTitle = mDialog.findViewById(R.id.tvTitle);
                addimgSP = mDialog.findViewById(R.id.AddimgSP);
                edTensp = mDialog.findViewById(R.id.edTenSP);
                edMotasp = mDialog.findViewById(R.id.edMotaSP);
                spnTH = mDialog.findViewById(R.id.spnTH);
                edgiaSP = mDialog.findViewById(R.id.edGiaSP);
                edLoaisp = mDialog.findViewById(R.id.edLoaiSP);
                edSoluongSP = mDialog.findViewById(R.id.edSoluongSP);
                tvAddSp = mDialog.findViewById(R.id.tvAddSP);
                tvCanclerAddSp = mDialog.findViewById(R.id.tvCanclerAdd);

                tvAddSp.setVisibility(View.GONE);
                tvTitle.setText("Chi tiết sản phẩm");
                linearLayoutSoluong.setVisibility(View.GONE);
                tvCanclerAddSp.setText("  ← Trờ lại  ");
                tvCanclerAddSp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });


                SanPham sp = arr.get(index);
                Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(addimgSP);
                edTensp.setText(arr.get(index).getTenSP());
                edMotasp.setText(arr.get(index).getMoTa());
                edgiaSP.setText(arr.get(index).getGiaSP() + " ₫");
                edLoaisp.setText(arr.get(index).getLoaiSP());

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
