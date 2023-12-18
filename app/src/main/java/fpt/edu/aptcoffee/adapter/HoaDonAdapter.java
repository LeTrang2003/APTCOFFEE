package fpt.edu.aptcoffee.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.HoaDonChiTietDAO;
import fpt.edu.aptcoffee.interfaces.ItemHoaDonOnClick;
import fpt.edu.aptcoffee.model.HoaDon;
import fpt.edu.aptcoffee.adapter.utils.XDate;
import fpt.edu.aptcoffee.model.HoaDonAPI;
import fpt.edu.aptcoffee.model.InvoiceData;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder>{
    Context context;
    List<HoaDonAPI> list;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    ItemHoaDonOnClick itemHoaDonOnClick;

    public HoaDonAdapter(Context context, List<HoaDonAPI> list, ItemHoaDonOnClick itemHoaDonOnClick) {
        this.context = context;
        this.list = list;
        this.hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
        this.itemHoaDonOnClick = itemHoaDonOnClick;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_hoa_don, parent, false);
        return new HoaDonViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDonAPI hoaDonAPI = list.get(position);
        if(hoaDonAPI == null){
            return;
        }
        holder.tvMaHoaDon.setText("HD0775098507"+hoaDonAPI.getNote());
        holder.tvtitlGioVao.setText(hoaDonAPI.getTimeIn());
        holder.tvGioVao.setText(hoaDonAPI.getTimeIn());
        holder.tvGioRa.setText(hoaDonAPI.getTimeOut());
        holder.tvGiaTien.setText(hoaDonAPI.getPrice()+" VND");
        holder.tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemHoaDonOnClick.itemOclick(view, hoaDonAPI);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHoaDon, tvtitlGioVao, tvGioVao, tvGioRa, tvGiaTien, tvChiTiet;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            tvtitlGioVao = itemView.findViewById(R.id.titleGioVao);
            tvGioVao = itemView.findViewById(R.id.tvGioVao);
            tvGioRa = itemView.findViewById(R.id.tvGioRa);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            tvChiTiet = itemView.findViewById(R.id.tvChiTiet);

        }
    }
}
