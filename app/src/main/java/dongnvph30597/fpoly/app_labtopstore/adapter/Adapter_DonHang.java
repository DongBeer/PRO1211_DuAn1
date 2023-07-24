package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.HoaDonChiTietDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_DonHang extends RecyclerView.Adapter<Adapter_DonHang.MyDHviewHolder>{
    private Context context;
    private ArrayList<DonHang> arr = new ArrayList<>();

    private UserDAO userDAO;
    private DonHangDAO donHangDAO;

    int trangThai = 0;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

    private ImageView imgbackCTDH;
    private TextView tvmaDHCTDH, tvtrangthaiCTDH, tvGhichuCTDH, tvTGCTDH, tvTongtienCTDH, tvTenKHCTDH, tvSDTCTDH, tvDiachiCTDH;
    private RecyclerView recyclerCTDH;

    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private Adapter_ChitietDonHang adapter;
    private ArrayList<ChiTietDonHang> arrCTDH = new ArrayList<>();

    public interface OnTrangThaiChangeListener {
        void onTrangThaiChanged(int position, int newTrangThai);
    }

    private OnTrangThaiChangeListener trangThaiChangeListener;



    public Adapter_DonHang(Context context, ArrayList<DonHang> arr) {
        this.context = context;
        this.arr = arr;
        donHangDAO = new DonHangDAO(context);
    }
    public void setOnTrangThaiChangeListener(OnTrangThaiChangeListener listener) {
        this.trangThaiChangeListener = listener;
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietDHDialog(holder.getAdapterPosition(),String.valueOf(holder.ckbTrangthai.getText()));
            }
        });
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
        String formattedPrice = decimalFormat.format(dh.getTongTien());
        holder.tvDHTongtien.setText(formattedPrice + " ₫");
        holder.imgDH.setImageResource(R.drawable.ic_hoadon);

        holder.ckbTrangthai.setOnCheckedChangeListener(null); // Bỏ bỏ lắng nghe sự kiện trước tiên để tránh lỗi vòng lặp không mong muốn.
        if (dh.getTrangThai() == 2) {
            holder.ckbTrangthai.setChecked(true);
            holder.ckbTrangthai.setFocusable(false);
            holder.ckbTrangthai.setText("Đã giao");
            return;
        }
        holder.ckbTrangthai.setFocusable(true);

        if(dh.getTrangThai() == 1){
            holder.ckbTrangthai.setChecked(false);
            holder.ckbTrangthai.setText("Đang giao");
        }

        holder.ckbTrangthai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int index = holder.getAdapterPosition();
                DonHang dh = arr.get(index);
                trangThai = dh.getTrangThai();
                if (trangThai == 2) {
                    // Đã giao hàng, không làm gì nữa nếu click vào checkbox.
                    return;
                }
                if (trangThai < 2 && isChecked) {
                    // Chỉ tăng trạng thái khi trạng thái chưa đạt 2 và checkbox được chọn.
                    trangThai++;
                    arr.get(index).setTrangThai(trangThai);
                    donHangDAO.updateTrangThaiDonHang(arr.get(index).getMaHD(), trangThai);
                    notifyDataSetChanged();

                    if (trangThaiChangeListener != null) {
                        trangThaiChangeListener.onTrangThaiChanged(index, trangThai);
                    }
                }
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

    public void ChiTietDHDialog(int index , String trangThai){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_chitietdonhang);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        imgbackCTDH = dialog.findViewById(R.id.imgbackCTHD);
        tvmaDHCTDH = dialog.findViewById(R.id.tvmaHDCTDH);
        tvtrangthaiCTDH = dialog.findViewById(R.id.tvtrangThaiCTDH);
        tvGhichuCTDH = dialog.findViewById(R.id.tvGhichuCTDH);
        tvTGCTDH = dialog.findViewById(R.id.tvthoigianCTDH);
        tvTongtienCTDH = dialog.findViewById(R.id.tvTongtienCTDH);
        tvTenKHCTDH = dialog.findViewById(R.id.tvTenKHCTDH);
        tvSDTCTDH = dialog.findViewById(R.id.tvSDTCTDH);
        tvDiachiCTDH = dialog.findViewById(R.id.tvDiachiKHHDCT);
        recyclerCTDH = dialog.findViewById(R.id.recyclerCTDH);

        imgbackCTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvmaDHCTDH.setText("0"+arr.get(index).getMaHD());
        tvtrangthaiCTDH.setText(trangThai);
        tvGhichuCTDH.setText(arr.get(index).getGhiChu());
        tvTGCTDH.setText(arr.get(index).getNgay());
        String formattedPrice1 = decimalFormat.format(arr.get(index).getTongTien());
        tvTongtienCTDH.setText(formattedPrice1 + " ₫");

        userDAO = new UserDAO(context);
        User user = userDAO.getID(String.valueOf(arr.get(index).getMaUser()));
        tvTenKHCTDH.setText(user.getHoTen());
        tvSDTCTDH.setText(user.getSoDT());
        tvDiachiCTDH.setText(user.getDiaChi());

        int maHD = arr.get(index).getMaHD();
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
        arrCTDH = hoaDonChiTietDAO.getHDCTbyIDmaHD(maHD);
        adapter = new Adapter_ChitietDonHang(context,arrCTDH);
        adapter.setData(arrCTDH);
        recyclerCTDH.setAdapter(adapter);
        dialog.show();
    }

}
