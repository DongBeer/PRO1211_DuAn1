package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.DAO.GioHangDAO;
import dongnvph30597.fpoly.app_labtopstore.DAO.SanPhamDAO;
import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.GioHang;
import dongnvph30597.fpoly.app_labtopstore.model.SanPham;
import dongnvph30597.fpoly.app_labtopstore.model.SharedPreferencesHelper;

public class Adapter_GioHang extends RecyclerView.Adapter<Adapter_GioHang.CartViewHolder> {

    private OnTotalPriceChangeListener onTotalPriceChangeListener;

    // Khai báo một interface để lắng nghe sự kiện khi tổng giá tiền thay đổi
    public interface OnTotalPriceChangeListener {
        void onTotalPriceChange(int totalPrice);
    }

    private Context context;
    private ArrayList<GioHang> arr = new ArrayList<>();

    private SanPhamDAO sanPhamDAO;
    private GioHangDAO gioHangDAO;

    int sl = 1;
    int giasp;
    int trangThai;

    private SharedPreferencesHelper preferencesHelper;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

    private ArrayList<Integer> selectedPositions = new ArrayList<>();

    public Adapter_GioHang(Context context, ArrayList<GioHang> arr) {
        this.context = context;
        this.arr = arr;
        gioHangDAO = new GioHangDAO(context);
        preferencesHelper = new SharedPreferencesHelper(context);
        selectedPositions = preferencesHelper.getCheckedItems();
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.onTotalPriceChangeListener = listener;
    }

    public void setData(ArrayList<GioHang> arrGH){
        this.arr = arrGH;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_giohang,parent,false);
        final CartViewHolder holder = new CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        GioHang gioHang = arr.get(position);

        sanPhamDAO = new SanPhamDAO(context);
        SanPham sp = sanPhamDAO.getID(String.valueOf(gioHang.getMaSP()));
        Glide.with(context).load(sp.getImgSP()).error(R.drawable.laptop1).into(holder.imgSPCart);
        holder.tvTenspCart.setText(sp.getTenSP());
        String formattedPrice = decimalFormat.format(sp.getGiaSP());
        holder.tvGiaspCart.setText(formattedPrice);
        holder.tvSoluongspCart.setText(String.valueOf(gioHang.getSoLuong()));

        holder.ckbMuaHang.setChecked(selectedPositions.contains(position));

        holder.ckbMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (holder.ckbMuaHang.isChecked()) {
                    selectedPositions.add(position);
                    trangThai = 1;
                    gioHang.setTrangThai(trangThai);
                    gioHangDAO.update(gioHang);
                } else {
                    selectedPositions.remove((Integer) position);
                    trangThai = 0;
                    gioHang.setTrangThai(trangThai);
                    gioHangDAO.update(gioHang);
                }

                preferencesHelper.saveCheckedItems(selectedPositions);

                // Thông báo cho Activity về sự thay đổi tổng giá tiền
                if (onTotalPriceChangeListener != null) {
                    onTotalPriceChangeListener.onTotalPriceChange(calculateTotal());
                }
            }
        });
        holder.tvCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = gioHang.getSoLuong() + 1;
                if(sl == sanPhamDAO.getSoLuongByMaSP(gioHang.getMaSP())+1){
                    sl = gioHang.getSoLuong();
                    Toast.makeText(context, "Số lượng không được vượt quá số lượng SP trong kho", Toast.LENGTH_SHORT).show();
                }
                gioHang.setSoLuong(sl); // Cập nhật số lượng trong danh sách arr
                gioHangDAO.update(gioHang); // Cập nhật số lượng trong cơ sở dữ liệu
                notifyDataSetChanged(); // Cập nhật lại danh sách sau khi thay đổi số lượng
                if(holder.ckbMuaHang.isChecked() == true){

                    if (onTotalPriceChangeListener != null) {
                        onTotalPriceChangeListener.onTotalPriceChange(calculateTotal());
                    }
                }

            }
        });
        holder.tvtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int sl = gioHang.getSoLuong() - 1;
                if (sl < 1) {
                    sl = 1;
                }

                gioHang.setSoLuong(sl); // Cập nhật số lượng trong danh sách arr
                gioHangDAO.update(gioHang); // Cập nhật số lượng trong cơ sở dữ liệu
                notifyDataSetChanged(); // Cập nhật lại danh sách sau khi thay đổi số lượng
                if(holder.ckbMuaHang.isChecked() == true){



                    if (onTotalPriceChangeListener != null) {
                        onTotalPriceChangeListener.onTotalPriceChange(calculateTotal());
                    }
                }else {
                    onTotalPriceChangeListener.onTotalPriceChange(0);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSPCart;
        private TextView tvTenspCart, tvGiaspCart, tvtru, tvCong, tvSoluongspCart;
        private CheckBox ckbMuaHang;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSPCart = itemView.findViewById(R.id.imgSPCart);
            tvTenspCart = itemView.findViewById(R.id.tvtenSPCart);
            tvGiaspCart = itemView.findViewById(R.id.tvGiaSPCart);
            tvtru = itemView.findViewById(R.id.tvtru);
            tvCong = itemView.findViewById(R.id.tvCong);
            tvSoluongspCart = itemView.findViewById(R.id.tvsoluongCart);
            ckbMuaHang = itemView.findViewById(R.id.ckbMuahang);
        }
    }

    public int calculateTotal() {
        int total = 0;
        if (selectedPositions.size() > 0 && arr.size() > 0) {
            for (int position : selectedPositions) {
                if (position >= 0 && position < arr.size()) {
                    GioHang gioHang = arr.get(position);
                    total += gioHang.getGiaSP() * gioHang.getSoLuong();
                }
            }
        }
        return total;
    }
    public void deleteItem(int position) {
        if (position >= 0 && position < arr.size()) {
            // Lấy thông tin giỏ hàng tại vị trí position
            GioHang gioHang = arr.get(position);

            // Xóa item trong cơ sở dữ liệu
            gioHangDAO.delete(String.valueOf(gioHang.getMaGH()));

            // Xóa item tại vị trí position trong arr
            arr.remove(position);


            // Xóa chỉ số tương ứng trong selectedPositions (nếu có)
            selectedPositions.remove((Integer) position);

            Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();

            // Cập nhật tổng giá tiền sau khi xóa thành công
            int total = calculateTotal();

            notifyDataSetChanged();
            // Thông báo cho Activity về sự thay đổi tổng giá tiền
            if (onTotalPriceChangeListener != null) {
                onTotalPriceChangeListener.onTotalPriceChange(total);
            }

        }
    }
}
