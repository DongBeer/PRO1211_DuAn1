package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_KhachHang extends RecyclerView.Adapter<Adapter_KhachHang.MyKHViewHolder> {
    private Context context;
    private ArrayList<User> arr = new ArrayList<>();

    private ImageView imgctkh;
    private TextView tvctmaKH, tvcthotenKH, tvcttdnKH, tvctSdtKH, tvctDcKH, tvBack;

    public Adapter_KhachHang(Context context, ArrayList<User> arr) {
        this.context = context;
        this.arr = arr;
    }

    public void setData(ArrayList<User> arrKH){
        this.arr = arrKH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyKHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_khachhang, parent,false);
        final MyKHViewHolder holder =new MyKHViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogChitietKH(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyKHViewHolder holder, int position) {
        Glide.with(context).load(arr.get(position).imgUser).error(R.drawable.user_login).into(holder.imgKH);
        holder.tvHotenKH.setText(arr.get(position).hoTen);
        holder.tvSodtKH.setText(arr.get(position).soDT);
        holder.imgCallKH.setImageResource(R.drawable.ic_call_kh);
        holder.imgCallKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = arr.get(holder.getAdapterPosition()).soDT;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyKHViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgKH, imgCallKH;
        private TextView tvHotenKH, tvSodtKH;
        public MyKHViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKH = itemView.findViewById(R.id.imgKH);
            tvHotenKH = itemView.findViewById(R.id.tvhotenKH);
            tvSodtKH = itemView.findViewById(R.id.tvSodtKH);
            imgCallKH = itemView.findViewById(R.id.imgCallKH);
        }
    }

    public void DialogChitietKH(int index){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_chitiet_kh);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgctkh = dialog.findViewById(R.id.circle_imgKH);
        tvcthotenKH = dialog.findViewById(R.id.tvcthotenKH);
        tvcttdnKH = dialog.findViewById(R.id.tvcttdnKH);
        tvctmaKH = dialog.findViewById(R.id.tvctmaKH);
        tvctSdtKH = dialog.findViewById(R.id.tvctSdtKH);
        tvctDcKH = dialog.findViewById(R.id.tvcDcKH);
        tvBack = dialog.findViewById(R.id.tvback);

        Glide.with(context).load(arr.get(index).imgUser).error(R.drawable.user_login).into(imgctkh);
        tvctmaKH.setText(String.valueOf(arr.get(index).maUser));
        tvcthotenKH.setText(arr.get(index).hoTen);
        tvcttdnKH.setText(arr.get(index).tenDangnhap);
        tvctSdtKH.setText(arr.get(index).soDT);
        tvctDcKH.setText(arr.get(index).diaChi);

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
