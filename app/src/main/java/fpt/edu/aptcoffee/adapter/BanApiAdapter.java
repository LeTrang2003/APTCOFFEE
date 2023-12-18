package fpt.edu.aptcoffee.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.interfaces.ItemBanOnClick;
import fpt.edu.aptcoffee.interfaces.ItemBanOnClick2;
import fpt.edu.aptcoffee.model.Ban;
import fpt.edu.aptcoffee.model.BanApi;

public class BanApiAdapter extends RecyclerView.Adapter<BanApiAdapter.BanViewHolder> {
    List<BanApi> list;
    ItemBanOnClick2 itemBanOnClick;

    public BanApiAdapter(List<BanApi> list, ItemBanOnClick2 itemBanOnClick) {
        this.list = list;
        this.itemBanOnClick = itemBanOnClick;
    }

    @NonNull
    @Override
    public BanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ban, parent, false);
        return new BanViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BanViewHolder holder, int position) {
        BanApi ban = list.get(position);
        if (ban == null){
            return;
        }
        if(ban.getStatus() == Ban.CON_TRONG){
            holder.ivHinhAnh.setImageResource(R.drawable.ic_quan_ly_ban_24_black);
        }else {
            holder.ivHinhAnh.setImageResource(R.drawable.ic_quan_ly_ban_24_brow);
        }
        holder.tvMaBan.setText("BO"+ban.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemBanOnClick.itemOclick(view, ban);
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

    public static class BanViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnh;
        TextView tvMaBan;
        CardView cardView;

        public BanViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            tvMaBan = itemView.findViewById(R.id.tvMaBan);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
