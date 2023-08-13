package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DanhGiaDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.DanhGia;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_DanhGia extends RecyclerView.Adapter<Adapter_DanhGia.MyDanhGiaViewHolder>{
    private Context context;
    private ArrayList<DanhGia> arr = new ArrayList<>();
    private UserDAO userDAO;
    private DanhGiaDAO danhGiaDAO;

    public Adapter_DanhGia(Context context, ArrayList<DanhGia> arr) {
        this.context = context;
        this.arr = arr;
        userDAO = new UserDAO(context);
        danhGiaDAO = new DanhGiaDAO(context);
    }

    @NonNull
    @Override
    public MyDanhGiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_danhgia,parent,false);
        final MyDanhGiaViewHolder holder = new MyDanhGiaViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int maUser = preferences.getInt("maUser", -1);
                if(maUser != -1){
                    return false;
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bạn có muốn xóa bình luận này?");
                    builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = holder.getAdapterPosition();
                            int id = arr.get(index).getMaDG();
                            danhGiaDAO.delete(String.valueOf(id));
                            arr = danhGiaDAO.getDanhGiaBymaSP1(arr.get(index).getMaSP());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setPositiveButton("Không",null);

                    builder.show();
                }
                return true;
            }
        });
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
