package dongnvph30597.fpoly.app_labtopstore.tools;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import dongnvph30597.fpoly.app_labtopstore.adapter.Adapter_GioHang;

public class DeleteItemGioHang extends ItemTouchHelper.SimpleCallback {

    private Context context;

    private Adapter_GioHang adapter;
    public DeleteItemGioHang(Context context,Adapter_GioHang adapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
            showDeleteConfirmationDialog(viewHolder.getAdapterPosition());
        }
    }

    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa sản phẩm");
        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.deleteItem(position);
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyItemChanged(position); // Nếu người dùng chọn "Không", thì cần cập nhật lại item trong RecyclerView
            }
        });
        builder.setCancelable(false); // Ngăn người dùng thoát khỏi hộp thoại bằng cách nhấn ra bên ngoài
        builder.show();
    }
}
