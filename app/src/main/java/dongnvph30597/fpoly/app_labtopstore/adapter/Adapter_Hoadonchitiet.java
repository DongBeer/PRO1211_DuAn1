package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;

public class Adapter_Hoadonchitiet extends RecyclerView.Adapter<Adapter_Hoadonchitiet.MyHDCTViewHolder>{
    private Context context;
    private ArrayList<GioHang> arr = new ArrayList<>();

    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

    private SanPhamDAO sanPhamDAO;
    public Adapter_Hoadonchitiet(Context context, ArrayList<GioHang> arr) {
        this.context = context;
        this.arr = arr;
    }

    public void setData(ArrayList<GioHang> arrGH){
        this.arr = arrGH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHDCTViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hdct,parent,false);
        final MyHDCTViewHolder holder = new MyHDCTViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHDCTViewHolder holder, int position) {
        GioHang gioHang = arr.get(position);

        sanPhamDAO = new SanPhamDAO(context);
        SanPham sp = sanPhamDAO.getID(String.valueOf(gioHang.getMaSP()));

        Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(holder.imgSPHdct);
        holder.tvTenspHdct.setText(sp.getTenSP());
        String formattedPrice = decimalFormat.format(sp.getGiaSP());
        holder.tvGiaspHdct.setText(formattedPrice);
        holder.tvSoluongspHDCT.setText("x"+gioHang.getSoLuong());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyHDCTViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSPHdct;
        private TextView tvTenspHdct, tvGiaspHdct, tvSoluongspHDCT;
        public MyHDCTViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSPHdct = itemView.findViewById(R.id.imgSPHDCT);
            tvTenspHdct = itemView.findViewById(R.id.tvtenSPHDCT);
            tvGiaspHdct = itemView.findViewById(R.id.tvGiaSPHDCT);
            tvSoluongspHDCT = itemView.findViewById(R.id.tvSoluongHDCT);
        }
    }

}
