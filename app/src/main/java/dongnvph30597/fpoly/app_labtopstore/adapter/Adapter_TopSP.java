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

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.TopSP;

public class Adapter_TopSP extends RecyclerView.Adapter<Adapter_TopSP.MyTopSPViewHolder>{
    private Context context;
    private ArrayList<TopSP> arr = new ArrayList<>();

    public Adapter_TopSP(Context context, ArrayList<TopSP> arr) {
        this.context = context;
        this.arr = arr;
    }

    public void setData(ArrayList<TopSP> arrTop){
        this.arr = arrTop;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyTopSPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_topsp,parent,false);
        final MyTopSPViewHolder holder = new MyTopSPViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTopSPViewHolder holder, int position) {
        TopSP topSP = arr.get(position);
        Glide.with(context).load(topSP.getImgSP()).into(holder.imgSPTop);
        holder.tvTenSPTop.setText(topSP.getTenSP());
        holder.tvsoluongSPTop.setText(topSP.getSoLuongBan()+"");
        holder.tvTop.setText("Top "+(position+1));
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyTopSPViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSPTop;
        private TextView tvTenSPTop, tvsoluongSPTop, tvTop;
        public MyTopSPViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSPTop = itemView.findViewById(R.id.imgSPtop);
            tvTenSPTop = itemView.findViewById(R.id.tvtenSPTop);
            tvsoluongSPTop = itemView.findViewById(R.id.tvsoluongTopsp);
            tvTop = itemView.findViewById(R.id.tvTop);
        }
    }
}
