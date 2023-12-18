package fpt.edu.aptcoffee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.interfaces.ItemHangHoaOnClick;
import fpt.edu.aptcoffee.model.HangHoa;
import fpt.edu.aptcoffee.model.SanPhamAPi;

public class ThucUongAdapter extends RecyclerView.Adapter<ThucUongAdapter.ThucUongViewHolder> {
    List<SanPhamAPi> list;
    ItemHangHoaOnClick itemHangHoaOnClick;

    private Context context;

    public ThucUongAdapter(List<SanPhamAPi> list, ItemHangHoaOnClick itemHangHoaOnClick, Context context) {
        this.list = list;
        this.itemHangHoaOnClick = itemHangHoaOnClick;
        this.context = context;
    }

    @NonNull
    @Override
    public ThucUongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thuc_uong, parent, false);
        return new ThucUongViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ThucUongViewHolder holder, int position) {
        SanPhamAPi hangHoa = list.get(position);
        if (hangHoa == null) {
            return;
        }
        if (hangHoa.getImage() != null && hangHoa.getImage().length > 0) {
            String imageUrl = hangHoa.getImage()[0]; // Accessing the first image
            Log.d("zzz", "onBindViewHolder: image url " + imageUrl);
            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.ic_error_warning_line) // Your error placeholder
                    .placeholder(R.drawable.ic_error_warning_line) // Optional placeholder
                    .into(holder.ivHinhAnh);


        }
        holder.tvTenHangHoa.setText(hangHoa.getName());
        holder.tvGiaTien.setText(hangHoa.getPrice() + "VND");
//        if (hangHoa.get() == 0) {
//            holder.tvTrangThai.setText("Hết hàng");
//            holder.tvTrangThai.setTextColor(Color.GRAY);
//        } else {
            holder.tvTrangThai.setText("Còn hàng");
            holder.tvTrangThai.setTextColor(Color.BLUE);
//        }
        holder.ivMenuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemHangHoaOnClick.itemOclick(view, hangHoa);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class ThucUongViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnh, ivMenuMore;
        TextView tvTenHangHoa, tvGiaTien, tvTrangThai;

        public ThucUongViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            ivMenuMore = itemView.findViewById(R.id.ivMenuMore);
            tvTenHangHoa = itemView.findViewById(R.id.tvTenHangHoa);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
