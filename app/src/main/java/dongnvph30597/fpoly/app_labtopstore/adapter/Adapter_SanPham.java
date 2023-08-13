package dongnvph30597.fpoly.app_labtopstore.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.MySPViewHolder>{
    private Context context;
    private ArrayList<SanPham> arr;
    private SanPhamDAO sanPhamDAO;



    private AdapterView.OnItemClickListener mListener;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");


    public Adapter_SanPham(Context context, SanPhamDAO sanPhamDAO, AdapterView.OnItemClickListener listener) {
        this.context = context;
        this.sanPhamDAO = sanPhamDAO;
        this.mListener = listener;
    }

    public void setData(ArrayList<SanPham> arrSP){
        this.arr = arrSP;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MySPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_sanpham,parent,false);
        final MySPViewHolder holder = new MySPViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySPViewHolder holder, int position) {
        SanPham sp = arr.get(position);
        Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(holder.imgSp);
        holder.tvtenSP.setText(sp.getTenSP());
        String formattedPrice = decimalFormat.format(sp.getGiaSP());
        holder.tvGiaSP.setText(formattedPrice);
        holder.tvSoluongSP.setText(String.valueOf(sp.getSoLuong()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(null,v,holder.getAdapterPosition(),v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MySPViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSp;
        private TextView tvtenSP, tvGiaSP, tvSoluongSP;
        private LinearLayout linear_onclick;
        public MySPViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSp = itemView.findViewById(R.id.imgSP);
            tvtenSP = itemView.findViewById(R.id.tvtenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvSoluongSP = itemView.findViewById(R.id.tvsoluongSP);
            linear_onclick = itemView.findViewById(R.id.onclick_linearSP);
        }
    }

}
