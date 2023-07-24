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
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;

public class Adapter_ChitietDonHang extends RecyclerView.Adapter<Adapter_ChitietDonHang.MyCTDHViewHolder>{
    private Context context;
    private ArrayList<ChiTietDonHang> arr = new ArrayList<>();

    private SanPhamDAO sanPhamDAO;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

    public Adapter_ChitietDonHang(Context context, ArrayList<ChiTietDonHang> arr) {
        this.context = context;
        this.arr = arr;
    }

    public void setData(ArrayList<ChiTietDonHang> arrCTDH){
        this.arr = arrCTDH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCTDHViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hdct,parent,false);
        final MyCTDHViewHolder holder = new MyCTDHViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCTDHViewHolder holder, int position) {
        ChiTietDonHang chiTietDonHang = arr.get(position);

        sanPhamDAO = new SanPhamDAO(context);
        SanPham sp = sanPhamDAO.getID(String.valueOf(chiTietDonHang.getMaSanPham()));
        Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(holder.imgSPHdct);
        holder.tvTenspHdct.setText(sp.getTenSP());
        String formattedPrice = decimalFormat.format(sp.getGiaSP());
        holder.tvGiaspHdct.setText(formattedPrice);
        holder.tvSoluongspHDCT.setText("x"+chiTietDonHang.getSoLuong());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyCTDHViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSPHdct;
        private TextView tvTenspHdct, tvGiaspHdct, tvSoluongspHDCT;
        public MyCTDHViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSPHdct = itemView.findViewById(R.id.imgSPHDCT);
            tvTenspHdct = itemView.findViewById(R.id.tvtenSPHDCT);
            tvGiaspHdct = itemView.findViewById(R.id.tvGiaSPHDCT);
            tvSoluongspHDCT = itemView.findViewById(R.id.tvSoluongHDCT);
        }
    }
}
