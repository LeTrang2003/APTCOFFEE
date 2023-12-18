package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.UserManager;
import fpt.edu.aptcoffee.adapter.BanAdapter;
import fpt.edu.aptcoffee.adapter.BanApiAdapter;
import fpt.edu.aptcoffee.dao.BanDAO;
import fpt.edu.aptcoffee.dao.HoaDonDAO;
import fpt.edu.aptcoffee.interfaces.ItemBanOnClick;
import fpt.edu.aptcoffee.interfaces.ItemBanOnClick2;
import fpt.edu.aptcoffee.model.Ban;
import fpt.edu.aptcoffee.model.BanApi;
import fpt.edu.aptcoffee.model.HoaDon;
import fpt.edu.aptcoffee.adapter.utils.MyToast;

public class QuanLyBanActivity extends AppCompatActivity {
    public static final String MA_BAN = "maBan";
    Toolbar toolbar;
    RecyclerView recyclerViewBan;
    BanDAO banDAO;
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ban);
        initToolBar();
        initView();
        banDAO = new BanDAO(this);
        hoaDonDAO = new HoaDonDAO(this);
        loadData();

    }

    private void initView() {
        recyclerViewBan = findViewById(R.id.recyclerViewBan);
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolbarBan);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadData() {

        UserManager.getInstance().callBan(new UserManager.OnLoadInfoListener() {
            @Override
            public void onSuccess() {
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(QuanLyBanActivity.this, 3);
                recyclerViewBan.setLayoutManager(linearLayoutManager);
                BanApiAdapter banAdapter = new BanApiAdapter(UserManager.getInstance().getBan(), new ItemBanOnClick2() {
                    @Override
                    public void itemOclick(View view, BanApi ban) {
                        if (ban.getStatus() == Ban.CON_TRONG) {
                            createNewHoaDon(ban);
                        } else {
                            Intent intent = new Intent(QuanLyBanActivity.this, OderActivity.class);
                            intent.putExtra(MA_BAN, String.valueOf(ban.getName()));
                            intent.putExtra("banApi", ban);
                            startActivity(intent);
                            overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
                        }
                    }
                });
                recyclerViewBan.setAdapter(banAdapter);
            }

            @Override
            public void onFailure(String e) {

            }

            @Override
            public void onAuthNull() {

            }
        });


    }

    private void createNewHoaDon(BanApi ban) {
        // tạo hoá đơn mới
        View viewDialog = LayoutInflater.from(QuanLyBanActivity.this).inflate(R.layout.layout_dialog_oder, null);
        Button btnOder = viewDialog.findViewById(R.id.btnOder);
        TextView tvBoQua = viewDialog.findViewById(R.id.tvBoQua);

        Dialog dialog = new Dialog(QuanLyBanActivity.this);
        dialog.setContentView(viewDialog);

        tvBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOder.setOnClickListener(view -> {
            ban.setStatus(Ban.CO_KHACH);
            UserManager.getInstance().updateBan(ban.get_id(),ban , new UserManager.OnLoadInfoListener() {
                @Override
                public void onSuccess() {

                    // tạo ra hoá đơn
                    Calendar c = Calendar.getInstance(); // lấy ngày thánh năm và giờ hiện tại
                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setMaBan(Integer.parseInt(ban.getName()));
                    hoaDon.setGioVao(c.getTime());
                    hoaDon.setGioRa(c.getTime());
                    hoaDon.setTrangThai(HoaDon.CHUA_THANH_TOAN);
                    hoaDonDAO.insertHoaDon(hoaDon);
                    dialog.dismiss();
                    loadData();
                }

                @Override
                public void onFailure(String e) {

                }

                @Override
                public void onAuthNull() {

                }
            });
        });
        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
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
            addBan();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addBan() {
        // Thêm bàn mới
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setMessage("Bạn có muốn thêm bàn mới?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Ban ban = new Ban();
                ban.setTrangThai(Ban.CON_TRONG);
                if (banDAO.insertBan(ban)) {
                    loadData();
                    MyToast.successful(QuanLyBanActivity.this, "Thêm bàn mới thành công");
                }
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}