package dongnvph30597.fpoly.app_labtopstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.time.temporal.Temporal;
import java.util.ArrayList;

import dongnvph30597.fpoly.app_labtopstore.R;
import dongnvph30597.fpoly.app_labtopstore.model.ThuongHieu;

public class Adapter_SpinerTH extends ArrayAdapter<ThuongHieu> {
    private Context context;
    private ArrayList<ThuongHieu> objects;
    private TextView tvspnmaTH, tvspntenTH;
    public Adapter_SpinerTH(@NonNull Context context,  ArrayList<ThuongHieu> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.layout_spiner_thuonghieu,null);

        }
        final ThuongHieu obj = objects.get(position);
        if (obj != null){
            tvspnmaTH = holder.findViewById(R.id.tvspnmaTH);
            tvspnmaTH.setText(String.valueOf(obj.maTH));
            tvspntenTH = holder.findViewById(R.id.tvspntenTH);
            tvspntenTH.setText(obj.tenTH);
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.layout_spiner_thuonghieu,null);

        }
        final ThuongHieu obj = objects.get(position);
        if (obj != null){
            tvspnmaTH = holder.findViewById(R.id.tvspnmaTH);
            tvspnmaTH.setText(String.valueOf(obj.maTH));
            tvspntenTH = holder.findViewById(R.id.tvspntenTH);
            tvspntenTH.setText(obj.tenTH);
        }
        return holder;
    }

}
