package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_Slide_LoaiSP extends RecyclerView.Adapter<Adapter_Slide_LoaiSP.MySlideLoaiSPViewHolder>{

    private Context context;
    private ArrayList<ThuongHieu> arr = new ArrayList<>();

    private AdapterView.OnItemClickListener mListener;

    public Adapter_Slide_LoaiSP(Context context, ArrayList<ThuongHieu> arr, AdapterView.OnItemClickListener listener) {
        this.context = context;
        this.arr = arr;
        this.mListener = listener;
    }

    public void setData(ArrayList<ThuongHieu> arrTH){
        this.arr = arrTH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MySlideLoaiSPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_imgloaisp,parent,false);
        final MySlideLoaiSPViewHolder holder = new MySlideLoaiSPViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MySlideLoaiSPViewHolder holder, int position) {
        ThuongHieu thuongHieu = arr.get(position);
        Glide.with(context).load(thuongHieu.getImgTH()).error(R.drawable.laptop1).into(holder.imgLoaiSPSlide);

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

    public class MySlideLoaiSPViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLoaiSPSlide;
        public MySlideLoaiSPViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoaiSPSlide = itemView.findViewById(R.id.imgLoaiSPSilde);
        }
    }
}
