package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.DanhGiaDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.DonHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.HoaDonChiTietDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.UserDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.activity.DatHang_Activity;
import dongnvph30597.fpoly.app_labtopstore.model.ChiTietDonHang;
import dongnvph30597.fpoly.app_labtopstore.model.DanhGia;
import dongnvph30597.fpoly.app_labtopstore.model.DonHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.User;

public class Adapter_DonHang extends RecyclerView.Adapter<Adapter_DonHang.MyDHviewHolder>{
    private Context context;
    private ArrayList<DonHang> arr = new ArrayList<>();
    private boolean isConfirmed = false;

    private UserDAO userDAO;
    private DonHangDAO donHangDAO;

    int trangThai = 0;
    int maHD;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

    private ImageView imgbackCTDH;
    private TextView tvmaDHCTDH, tvtrangthaiCTDH, tvGhichuCTDH, tvTGCTDH, tvTongtienCTDH, tvTenKHCTDH, tvSDTCTDH, tvDiachiCTDH, tvXacnhanhang;
    private RecyclerView recyclerCTDH;
    private CardView cvbtnXacnhan;
    int rating;

    private HoaDonChiTietDAO hoaDonChiTietDAO;
    private Adapter_ChitietDonHang adapter;
    private ArrayList<ChiTietDonHang> arrCTDH = new ArrayList<>();
    int maUser;

    public interface OnTrangThaiChangeListener {
        void onTrangThaiChanged(int position, int newTrangThai);
    }

    private OnTrangThaiChangeListener trangThaiChangeListener;



    public Adapter_DonHang(Context context, ArrayList<DonHang> arr) {
        this.context = context;
        this.arr = arr;
        donHangDAO = new DonHangDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
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

                ChiTietDHDialog(holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyDHviewHolder holder, int position) {
        DonHang dh = arr.get(position);
        maHD = dh.getMaHD();
        holder.tvDHmaHD.setText("0"+dh.getMaHD());

        userDAO = new UserDAO(context);
        User u = userDAO.getID(String.valueOf(dh.getMaUser()));
        holder.tvDHtenKH.setText(u.getHoTen());

        holder.tvDHNgay.setText(dh.getNgay());
        String formattedPrice = decimalFormat.format(dh.getTongTien());
        holder.tvDHTongtien.setText(formattedPrice + " ₫");
        holder.imgDH.setImageResource(R.drawable.ic_hoadon);

        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        maUser = preferences.getInt("maUser", -1);

        if(maUser != -1){
            holder.ckbTrangthai.setVisibility(View.GONE);
            holder.ckbTrangthai.setFocusable(false);
        }

        if(maUser != -1 && dh.getTrangThai() == 0 || dh.getTrangThai() == 1){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    BottomSheetDialog dialog = new BottomSheetDialog(context);
                    dialog.setContentView(R.layout.layout_bottomsheetdialog_huydonhang);
                    CardView cardView  = dialog.findViewById(R.id.cvbtnHuyDH);
                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = holder.getAdapterPosition();


                            ArrayList<ChiTietDonHang> arrCTDH = hoaDonChiTietDAO.getHDCTbyIDmaHD(dh.getMaHD());

                            for(ChiTietDonHang ctdh : arrCTDH){
                                int maSP = ctdh.getMaSanPham();
                                int sl = ctdh.getSoLuong();

                                SanPhamDAO sanPhamDAO = new SanPhamDAO(context);
                                int slbd = sanPhamDAO.getSoLuongByMaSP(maSP);
                                int updatesl = slbd + sl;

                                SanPham sanPham = new SanPham();
                                sanPham.setMaSP(maSP);
                                sanPham.setSoLuong(updatesl);
                                sanPhamDAO.updateSoLuong(sanPham);


                            }

                            Toast.makeText(context, "Bạn đã hủy đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                            donHangDAO.delete(String.valueOf(dh.getMaHD()));
                            donHangDAO.deleteHoaDonChiTietByMaHD(String.valueOf(dh.getMaHD()));


                            dialog.dismiss();

                            if (trangThaiChangeListener != null) {
                                trangThaiChangeListener.onTrangThaiChanged(index, trangThai);
                            }
                            notifyDataSetChanged();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }

        if (dh.getTrangThai() == 3) {
            holder.ckbTrangthai.setFocusable(false);
            holder.ckbTrangthai.setText("Đã giao");
            holder.ckbTrangthai.setVisibility(View.GONE);
            return;
        }
        holder.ckbTrangthai.setFocusable(true);

        if(dh.getTrangThai() == 1){
            holder.ckbTrangthai.setText("Giao hàng");
        }
        if(dh.getTrangThai() == 2){
            holder.ckbTrangthai.setText("Đã giao");
        }


        holder.ckbTrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = holder.getAdapterPosition();
                DonHang dh = arr.get(index);
                trangThai = dh.getTrangThai();
                if (trangThai == 3) {
                    return;
                }
                if (trangThai == 0) {

                    trangThai = 1;
                    arr.get(index).setTrangThai(trangThai);
                    donHangDAO.updateTrangThaiDonHang(arr.get(index).getMaHD(), trangThai);
                    if (trangThaiChangeListener != null) {
                        trangThaiChangeListener.onTrangThaiChanged(index, trangThai);
                    }


                    notifyDataSetChanged();


                }else if(trangThai == 1){
                    trangThai = 2;
                    arr.get(index).setTrangThai(trangThai);
                    donHangDAO.updateTrangThaiDonHang(arr.get(index).getMaHD(), trangThai);
                    if (trangThaiChangeListener != null) {
                        trangThaiChangeListener.onTrangThaiChanged(index, trangThai);
                    }
                    notifyDataSetChanged();

                }else if(trangThai == 2){
                    trangThai = 3;
                    arr.get(index).setTrangThai(trangThai);
                    donHangDAO.updateTrangThaiDonHang(arr.get(index).getMaHD(), trangThai);


                    if (trangThaiChangeListener != null) {
                        trangThaiChangeListener.onTrangThaiChanged(index, trangThai);
                    }
                    notifyDataSetChanged();
                }
            }
        });

        }



    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyDHviewHolder extends RecyclerView.ViewHolder {
        private TextView tvDHmaHD, tvDHtenKH, tvDHNgay, tvDHTongtien, ckbTrangthai;
        private ImageView imgDH;
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

    public void ChiTietDHDialog(int index){
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
        cvbtnXacnhan = dialog.findViewById(R.id.cvbtnxacnhan1);
        tvXacnhanhang = dialog.findViewById(R.id.tvXacnhanhang);

        if(maUser != -1 && arr.get(index).getTrangThai() == 3) {
            int maHD = arr.get(index).getMaHD();
            int DG = donHangDAO.getTrangThaiDG(maHD);

            if(DG == 1 ) {
                cvbtnXacnhan.setVisibility(View.GONE);
                tvXacnhanhang.setVisibility(View.VISIBLE);
                tvXacnhanhang.setText("Bạn đã đánh giá đơn hàng này");
            } else {
                cvbtnXacnhan.setVisibility(View.VISIBLE);
                tvXacnhanhang.setVisibility(View.VISIBLE);
                cvbtnXacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        donHangDAO.updateTrangThaiDG(arr.get(index).getMaHD(),1);

                        BottomSheetDialog adialog = new BottomSheetDialog(context);
                        adialog.setContentView(R.layout.layout_bottomdialog_danhgia);
                        ImageView imgStar1, imgStar2, imgStar3, imgStar4, imgStar5;
                        imgStar1 = adialog.findViewById(R.id.imgstar1);
                        imgStar2 = adialog.findViewById(R.id.imgstar2);
                        imgStar3 = adialog.findViewById(R.id.imgstar3);
                        imgStar4 = adialog.findViewById(R.id.imgstar4);
                        imgStar5 = adialog.findViewById(R.id.imgstar5);
                        EditText edBinhluan = adialog.findViewById(R.id.edBinhluan);
                        CardView cvguidanhgia = adialog.findViewById(R.id.cvguidanhgia);

                        rating = 0;

                        // Thêm sự kiện click cho từng sao
                        imgStar1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rating = 1;
                                setStarImages(imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, rating);
                            }
                        });

                        imgStar2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rating = 2;
                                setStarImages(imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, rating);
                            }
                        });

                        imgStar3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rating = 3;
                                setStarImages(imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, rating);
                            }
                        });

                        imgStar4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rating = 4;
                                setStarImages(imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, rating);
                            }
                        });

                        imgStar5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rating = 5;
                                setStarImages(imgStar1, imgStar2, imgStar3, imgStar4, imgStar5, rating);
                            }
                        });

                        cvguidanhgia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String bl = edBinhluan.getText().toString().trim();
                                if (bl.isEmpty()) {
                                    bl = "Nothing";
                                }
                                ArrayList<Integer> maSanPhamTrongHoaDon = new ArrayList<>();
                                for (ChiTietDonHang hdt : arrCTDH) {
                                    maSanPhamTrongHoaDon.add(hdt.getMaSanPham());
                                    addDanhGiaToHoaDonChiTiet(hdt.getMaSanPham(), rating, bl);
                                    tvXacnhanhang.setText("Bạn đã đánh giá đơn hàng này");
                                    cvbtnXacnhan.setVisibility(View.GONE);
                                    adialog.dismiss();
                                }

                                // Thêm đánh giá vào các sản phẩm trong cùng một đơn hàng

                            }
                        });

                        adialog.setCancelable(false);
                        adialog.show();
                    }
                });
            }
        }

        imgbackCTDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvmaDHCTDH.setText("0"+arr.get(index).getMaHD());
        if(arr.get(index).getTrangThai() == 0){
            tvtrangthaiCTDH.setText("Chờ xử lý");
        }else if(arr.get(index).getTrangThai() == 1){
            tvtrangthaiCTDH.setText("Đã xác nhận");
        }else if(arr.get(index).getTrangThai() == 2){
            tvtrangthaiCTDH.setText("Đang giao");
        }else {
            tvtrangthaiCTDH.setText("Đã giao");
        }

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

    // cập nhật hình ảnh sao tương ứng với số sao đang được đánh giá
    private void setStarImages(ImageView imgStar1, ImageView imgStar2, ImageView imgStar3, ImageView imgStar4, ImageView imgStar5, int rating) {
        imgStar1.setImageResource(rating >= 1 ? R.drawable.icon_star1 : R.drawable.icon_star0);
        imgStar2.setImageResource(rating >= 2 ? R.drawable.icon_star1 : R.drawable.icon_star0);
        imgStar3.setImageResource(rating >= 3 ? R.drawable.icon_star1 : R.drawable.icon_star0);
        imgStar4.setImageResource(rating >= 4 ? R.drawable.icon_star1 : R.drawable.icon_star0);
        imgStar5.setImageResource(rating >= 5 ? R.drawable.icon_star1 : R.drawable.icon_star0);
    }

    private void addDanhGiaToHoaDonChiTiet(int maSP, int rating, String binhLuan) {
        // TODO: Thêm đánh giá vào các sản phẩm trong cùng một đơn hàng
        DanhGiaDAO danhGiaDAO = new DanhGiaDAO(context);
        HoaDonChiTietDAO hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
        ArrayList<ChiTietDonHang> arrCTDH = hoaDonChiTietDAO.getHoaDonChiTietByMaSP(maSP);

        // Danh sách các sản phẩm đã được đánh giá
        ArrayList<Integer> danhSachDaDanhGia = new ArrayList<>();

        for (ChiTietDonHang ctdh : arrCTDH) {
            int maSanPham = ctdh.getMaSanPham();

            // Kiểm tra xem sản phẩm đã được đánh giá hay chưa
            if (!danhSachDaDanhGia.contains(maSanPham)) {
                danhSachDaDanhGia.add(maSanPham);

                DanhGia danhGiaMoi = new DanhGia();
                danhGiaMoi.setMaUser(maUser); // Lấy mã người dùng từ SharedPreferences
                danhGiaMoi.setMaSP(maSanPham);
                danhGiaMoi.setDangGia(rating);
                danhGiaMoi.setNhanXet(binhLuan);

                if (danhGiaDAO.insert(danhGiaMoi) > 0) {
                    Toast.makeText(context, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
