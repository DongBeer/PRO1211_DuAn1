package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.ImageSlide;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private Context context;
    private List<ImageSlide> list;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ImageSlide> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        ImageSlide obj = list.get(position);
        if (obj == null) {
            return;
        }
        holder.img.setImageResource(obj.getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_photo);
        }
    }
}
