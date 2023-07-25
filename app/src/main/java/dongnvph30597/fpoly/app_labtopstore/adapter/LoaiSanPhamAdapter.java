package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.DAO.ThuongHieuDao;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.LoaiSanPhamViewHolder> {

    private Context context;
    private List<ThuongHieu> list;
    private ThuongHieuDao dao;

    public interface onItemClickSelected{
        void onItemClick(int position);
    }

    private onItemClickSelected mListener;

    public LoaiSanPhamAdapter(Context context, ThuongHieuDao dao) {
        this.context = context;
        this.dao = dao;
    }

    public void setData(List<ThuongHieu> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void updateList(List<ThuongHieu> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public void setOnItemClickSelected(onItemClickSelected listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public LoaiSanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoaiSanPhamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_loai_san_pham, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSanPhamViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThuongHieu obj = list.get(position);
        if (obj == null) {
            return;
        }
        Glide.with(context).load(obj.getImgTH()).error(R.drawable.signup).into(holder.img);
        holder.name.setText(obj.getTenTH());


        holder.layoutSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null) return list.size();
        return 0;
    }

    public class LoaiSanPhamViewHolder extends RecyclerView.ViewHolder{
        private TextView id, name;
        private ImageView img, icEdit, icDelete;
        private LinearLayout layoutSelected;

        public LoaiSanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            img = itemView.findViewById(R.id.img_logo);
            layoutSelected = itemView.findViewById(R.id.layout_selected);
        }
    }

}
