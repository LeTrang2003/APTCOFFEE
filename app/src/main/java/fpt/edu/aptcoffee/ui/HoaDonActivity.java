package fpt.edu.aptcoffee.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.UserManager;
import fpt.edu.aptcoffee.adapter.HoaDonAdapter;
import fpt.edu.aptcoffee.dao.HoaDonDAO;
import fpt.edu.aptcoffee.interfaces.ItemHoaDonOnClick;
import fpt.edu.aptcoffee.model.HoaDon;
import fpt.edu.aptcoffee.model.HoaDonAPI;

public class HoaDonActivity extends AppCompatActivity {
    public static final String MA_HOA_DON = "maHoaDon";
    Toolbar toolbar;
    RecyclerView recyclerViewHoaDon;
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        initToolBar();

        initView();
        hoaDonDAO = new HoaDonDAO(this);
        loadData();

    }

    private void loadData() {
       UserManager.getInstance().callHoaDon(new UserManager.OnLoadInfoListener() {
           @Override
           public void onSuccess() {
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HoaDonActivity.this, RecyclerView.VERTICAL, false);
               recyclerViewHoaDon.setLayoutManager(linearLayoutManager);

               HoaDonAdapter adapter = new HoaDonAdapter(HoaDonActivity.this, UserManager.getInstance().getListHoaDon(), new ItemHoaDonOnClick() {
                   @Override
                   public void itemOclick(View view, HoaDonAPI hoaDon) {
                       Intent intent = new Intent(HoaDonActivity.this, ChiTietHoaDonActivity.class);
                       intent.putExtra(MA_HOA_DON, String.valueOf(hoaDon.getNote()));
                       startActivity(intent);
                       overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                   }
               });
               recyclerViewHoaDon.setAdapter(adapter);
           }

           @Override
           public void onFailure(String e) {

           }

           @Override
           public void onAuthNull() {

           }
       });
    }

    private void initView() {
        recyclerViewHoaDon = findViewById(R.id.recyclerViewHoaDon);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarHoaDon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}