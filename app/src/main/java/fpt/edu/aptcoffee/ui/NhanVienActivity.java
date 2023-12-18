package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import java.util.List;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.adapter.NguoiDungAdapter;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.interfaces.API;
import fpt.edu.aptcoffee.interfaces.ItemNguoiDungOnClick;
import fpt.edu.aptcoffee.adapter.utils.MyToast;
import fpt.edu.aptcoffee.model.NguoiDungAPI;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NhanVienActivity extends AppCompatActivity {
    public static final String MA_NGUOI_DUNG = "maNguoiDung";
    Toolbar toolBar;
    RecyclerView recyclerViewNhanVien;
    NguoiDungDAO nguoiDungDAO;
    private Retrofit retrofit;
    private API service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        initToolBar();
        initView();
        nguoiDungDAO = new NguoiDungDAO(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_GET)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(API.class);

        loadData();

    }

    private void initView() {
        recyclerViewNhanVien = findViewById(R.id.recyclerViewNhanVien);
    }

    private void initToolBar() {
        toolBar = findViewById(R.id.toolbarNhanVien);
        setSupportActionBar(toolBar);
    }

    private void loadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NhanVienActivity.this, RecyclerView.VERTICAL, false);
        recyclerViewNhanVien.setLayoutManager(linearLayoutManager);

        Call<List<NguoiDungAPI>> call = service.getNguoiDung();
call.enqueue(new retrofit2.Callback<List<NguoiDungAPI>>() {
            @Override
            public void onResponse(Call<List<NguoiDungAPI>> call, retrofit2.Response<List<NguoiDungAPI>> response) {
                List<NguoiDungAPI> nguoiDungAPIS = response.body();
                if (nguoiDungAPIS != null) {
                    NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter(nguoiDungAPIS, new ItemNguoiDungOnClick() {
                        @Override
                        public void itemOclick(View view, NguoiDungAPI nguoiDung) {
                            PopupMenu popup = new PopupMenu(NhanVienActivity.this, view);
                            popup.getMenuInflater()
                                    .inflate(R.menu.menu_more_nhan_vien, popup.getMenu());
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @SuppressLint("NonConstantResourceId")
                                public boolean onMenuItemClick(MenuItem item) {
                                    switch (item.getItemId()) {
                                        case R.id.menu_update:
                                            openUpdateNhanVienActivity(nguoiDung);
                                            break;
                                        case R.id.menu_delete:
                                            deleteNhanVien(nguoiDung);
                                            break;
                                        case R.id.menu_chitet:
                                            openChiTietNhanVienActivity(nguoiDung);
                                            break;
                                    }

                                    return true;
                                }
                            });

                            popup.show();

                        }
                    });
                    recyclerViewNhanVien.setAdapter(nguoiDungAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<NguoiDungAPI>> call, Throwable t) {
                MyToast.error(NhanVienActivity.this, "Lỗi kết nối");
            }
        });

    }

    private void openChiTietNhanVienActivity(NguoiDungAPI nguoiDung) {
        Intent intent = new Intent(NhanVienActivity.this, ChiTietNhanVienActivity.class);
        intent.putExtra(MA_NGUOI_DUNG, nguoiDung.get_id());
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private void openUpdateNhanVienActivity(NguoiDungAPI nguoiDung) {
        Intent intent = new Intent(NhanVienActivity.this, CapNhatNhanVienActivity.class);
        intent.putExtra(MA_NGUOI_DUNG, nguoiDung.get_id());
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private void deleteNhanVien(NguoiDungAPI nguoiDung) {
        // Xoá nhân viên
        AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this, R.style.AlertDialogTheme);
        builder.setMessage("Xóa nhân viên " + nguoiDung.getUsername() + "?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nguoiDungDAO.deleteNguoiDung(nguoiDung.get_id())) {
                    MyToast.successful(NhanVienActivity.this, "Xóa thành công");
                } else {
                    MyToast.error(NhanVienActivity.this, "Xóa không thành công");
                }
                loadData();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            startActivity(new Intent(NhanVienActivity.this, ThemNhanVienActivity.class));
            overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}