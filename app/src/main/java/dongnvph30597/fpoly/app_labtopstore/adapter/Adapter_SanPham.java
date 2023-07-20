package dongnvph30597.fpoly.app_labtopstore.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_SanPham extends RecyclerView.Adapter<Adapter_SanPham.MySPViewHolder>{
    private Context context;
    private ArrayList<SanPham> arr = new ArrayList<>();

    public interface Onclick {
        void onItemClick(int position);
    }
    private Onclick onclick;



    public Adapter_SanPham(Context context, ArrayList<SanPham> arr) {
        this.context = context;
        this.arr = arr;
    }
    public void setOnclick(Onclick onclick){
        this.onclick = onclick;
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
    public void onBindViewHolder(@NonNull MySPViewHolder holder,  int position) {

        Glide.with(context).load(arr.get(position).imgSP).error(R.drawable.laptop1).into(holder.imgSp);
        holder.tvtenSP.setText(arr.get(position).getTenSP());
        holder.tvGiaSP.setText(String.valueOf(arr.get(position).getGiaSP()));
        holder.tvSoluongSP.setText(String.valueOf(arr.get(position).getSoLuong()));

        holder.linear_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    onclick.onItemClick(holder.getAdapterPosition());

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
