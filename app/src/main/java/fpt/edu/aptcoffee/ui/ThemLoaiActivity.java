package fpt.edu.aptcoffee.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.dao.LoaiHangDAO;
import fpt.edu.aptcoffee.interfaces.API;
import fpt.edu.aptcoffee.model.LoaiHang;
import fpt.edu.aptcoffee.adapter.utils.ImageToByte;
import fpt.edu.aptcoffee.adapter.utils.MyToast;
import fpt.edu.aptcoffee.model.LoaiHangAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemLoaiActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE = 1;
    boolean pickImageSatus = false;

    ImageView ivBack, ivHinhAnh, ivPickImage;
    TextInputLayout tilTenLoai;
    Button btnAdd;
    LoaiHangDAO loaiHangDAO;

    private Retrofit retrofit;

    private API service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai);
        initView();

        loaiHangDAO = new LoaiHangDAO(this);
        retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_POST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(API.class);

        ivBack.setOnClickListener(this);
        ivPickImage.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        ivHinhAnh = findViewById(R.id.ivHinhAnh);
        ivPickImage = findViewById(R.id.ivPickImage);
        tilTenLoai = findViewById(R.id.tilTenLoaiHang);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void addProduct() {
        // Thêm loại hàng mới
        if (!pickImageSatus) {
            MyToast.error(ThemLoaiActivity.this, "Vui lòng chọn ảnh");
        } else {
            String tenLoai = Objects.requireNonNull(tilTenLoai.getEditText()).getText().toString().trim();
            if (tenLoai.isEmpty()) {
                MyToast.error(ThemLoaiActivity.this, "Vui lòng nhập tên loại");
            } else {
                LoaiHangAPI loaiHang = new LoaiHangAPI();
                loaiHang.setName(tenLoai);
                loaiHang.setImage(filePath);

                service.postLoaiHang(loaiHang).enqueue(new Callback<LoaiHangAPI>() {
                    @Override
                    public void onResponse(Call<LoaiHangAPI> call, Response<LoaiHangAPI> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ThemLoaiActivity.this, "Oke", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else {
                            try {
                                // Convert response body to string for debugging
                                String errorBody = response.errorBody().string();
                                Log.e("API Response", "Error: " + errorBody);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoaiHangAPI> call, Throwable t) {

                    }
                });

            }
        }
    }

    private void pickImage() {
        // Chọn hình trong Phone
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // cấp quyền cho ứng dụng
            ActivityCompat.requestPermissions(ThemLoaiActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_in_left, R.anim.anim_out_right);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                InputStream stream = getContentResolver().openInputStream(uri);

                // Convert InputStream to Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(stream);

                // Convert Bitmap to byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                // Convert Uri to file path
                filePath = getRealPathFromURI(uri);

                // Now you can use the filePath as needed

                // Display the retrieved image in ImageView
                pickImageSatus = true;
                ivHinhAnh.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String filePath;

    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        if (cursor == null) return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivPickImage:
                pickImage();
                break;
            case R.id.btnAdd:
                addProduct();
                break;
        }
    }
}