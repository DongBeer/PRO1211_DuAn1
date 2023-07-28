package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.DanhGia;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_DanhGia extends RecyclerView.Adapter<Adapter_DanhGia.MyDanhGiaViewHolder>{
    private Context context;
    private ArrayList<DanhGia> arr = new ArrayList<>();
    private UserDAO userDAO;

    public Adapter_DanhGia(Context context, ArrayList<DanhGia> arr) {
        this.context = context;
        this.arr = arr;
        userDAO = new UserDAO(context);
    }

    @NonNull
    @Override
    public MyDanhGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_danhgia,parent,false);
        final MyDanhGiaViewHolder holder = new MyDanhGiaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDanhGiaViewHolder holder, int position) {
        DanhGia dg = arr.get(position);
        User u = userDAO.getID(String.valueOf(dg.getMaUser()));
        holder.tvTenDNUser.setText(u.getTenDangnhap()+":");
        holder.tvNhanxet.setText(dg.getNhanXet());
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyDanhGiaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenDNUser, tvNhanxet;
        public MyDanhGiaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDNUser = itemView.findViewById(R.id.tvTenDNUser);
            tvNhanxet = itemView.findViewById(R.id.tvNhanxet);
        }
    }
}
