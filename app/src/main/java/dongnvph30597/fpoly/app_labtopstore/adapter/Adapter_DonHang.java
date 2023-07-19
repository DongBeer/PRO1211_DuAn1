package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_DonHang extends RecyclerView.Adapter<Adapter_DonHang.MyDHviewHolder>{
    private Context context;
    private ArrayList<DonHang> arr = new ArrayList<>();

    private UserDAO userDAO;
    private DonHangDAO donHangDAO;

    public Adapter_DonHang(Context context, ArrayList<DonHang> arr) {
        this.context = context;
        this.arr = arr;
        donHangDAO = new DonHangDAO(context);
    }

    public void setData(ArrayList<DonHang> arrDH){
        this.arr = arrDH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyDHviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_donhang,parent,false);
        final MyDHviewHolder holder = new MyDHviewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDHviewHolder holder, int position) {
        DonHang dh = arr.get(position);
        holder.tvDHmaHD.setText(String.valueOf(dh.getMaHD()));

        userDAO = new UserDAO(context);
        User u = userDAO.getID(String.valueOf(dh.getMaUser()));
        holder.tvDHtenKH.setText(u.getHoTen());

        holder.tvDHNgay.setText(dh.getNgay());
        holder.tvDHTongtien.setText(String.valueOf(dh.getTongTien()));
        holder.imgDH.setImageResource(R.drawable.ic_hoadon);

        if (dh.getTrangThai() == 1) {
            holder.ckbTrangthai.setOnCheckedChangeListener(null);
            holder.ckbTrangthai.setChecked(true);
            holder.ckbTrangthai.setFocusable(false); // Ngăn người dùng tương tác với CheckBox
            return;
        }

        holder.ckbTrangthai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int index = holder.getAdapterPosition();
                if(isChecked){
                    arr.get(index).setTrangThai(1);
                }else {
                    arr.get(index).setTrangThai(0);
                }

                DonHang d = new DonHang(arr.get(index).getMaHD(),arr.get(index).getMaUser()
                        ,arr.get(index).getMaAdmin(),arr.get(index).getNgay(),arr.get(index).getTongTien(),arr.get(index).getTrangThai());
                donHangDAO.update(d);
                arr = donHangDAO.getAllDonHang();
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyDHviewHolder extends RecyclerView.ViewHolder {
        private TextView tvDHmaHD, tvDHtenKH, tvDHNgay, tvDHTongtien;
        private ImageView imgDH;
        private CheckBox ckbTrangthai;
        public MyDHviewHolder(@NonNull View itemView) {
            super(itemView);
            tvDHmaHD = itemView.findViewById(R.id.tvdhmaHD);
            tvDHtenKH = itemView.findViewById(R.id.tvdhTenKH);
            tvDHNgay = itemView.findViewById(R.id.tvdhTg);
            tvDHTongtien = itemView.findViewById(R.id.tvdhTongtien);
            imgDH = itemView.findViewById(R.id.imgHD);
            ckbTrangthai = itemView.findViewById(R.id.ckbTrangthai);
        }
    }

}
