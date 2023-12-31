package fpt.edu.aptcoffee.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.interfaces.ItemNguoiDungOnClick;
import fpt.edu.aptcoffee.model.NguoiDung;
import fpt.edu.aptcoffee.model.NguoiDungAPI;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.NguoiDungViewHodel>{
    List<NguoiDungAPI> list;
    ItemNguoiDungOnClick itemNguoiDungOnClick;

    public NguoiDungAdapter(List<NguoiDungAPI> list, ItemNguoiDungOnClick itemNguoiDungOnClick) {
        this.list = list;
        this.itemNguoiDungOnClick = itemNguoiDungOnClick;
    }

    @NonNull
    @Override
    public NguoiDungViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_nhan_vien, parent, false);
        return new NguoiDungViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHodel holder, int position) {
        NguoiDungAPI nguoiDung = list.get(position);
        if (nguoiDung == null){
            return;
        }

        holder.tvTenNguoiDung.setText(nguoiDung.getUsername());
        holder.tvEmail.setText(nguoiDung.getEmail());
        Picasso.get().load(nguoiDung.getAvatar()).into(holder.ivHinhAnh);
        holder.ivMenuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemNguoiDungOnClick.itemOclick(view, nguoiDung);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    public static class NguoiDungViewHodel extends RecyclerView.ViewHolder {
        CircleImageView ivHinhAnh;
        TextView tvEmail, tvTenNguoiDung;
        ImageView ivMenuMore;

        public NguoiDungViewHodel(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            ivMenuMore = itemView.findViewById(R.id.ivMenuMore);

        }
    }

}
