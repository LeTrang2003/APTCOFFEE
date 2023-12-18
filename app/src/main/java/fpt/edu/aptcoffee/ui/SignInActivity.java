package fpt.edu.aptcoffee.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import fpt.edu.aptcoffee.MainActivity;
import fpt.edu.aptcoffee.R;
import fpt.edu.aptcoffee.UserManager;
import fpt.edu.aptcoffee.adapter.LoaiHangAdapter;
import fpt.edu.aptcoffee.client.ApiClient;
import fpt.edu.aptcoffee.dao.NguoiDungDAO;
import fpt.edu.aptcoffee.interfaces.API;
import fpt.edu.aptcoffee.interfaces.ItemLoaiHangOnClick;
import fpt.edu.aptcoffee.model.LoaiHangAPI;
import fpt.edu.aptcoffee.model.LoginBody;
import fpt.edu.aptcoffee.model.NguoiDungAPI;
import fpt.edu.aptcoffee.notification.MyNotification;
import fpt.edu.aptcoffee.adapter.utils.Loading;
import fpt.edu.aptcoffee.adapter.utils.MyToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SUCCESSFUL = "login successful";
    public static final String FAILE = "login faile";
    public static final String ERORR = "login error";
    public static final String STATUS_LOGIN = "status login";
    public static final String ACTION_LOGIN = "action login";
    public static final String KEY_USER = "maNguoiDung";
    Loading loading;
    TextInputLayout tilUserName, tilPassword;
    Button btnSignIn;
    TextView tvSignUp;
    NguoiDungDAO nguoiDungDAO;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
        initIntentFilter();
        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

        loading = new Loading(SignInActivity.this);

        loading.startLoading();


        UserManager.getInstance().callUsers(new UserManager.OnLoadInfoListener() {
            @Override
            public void onSuccess() {
                loading.stopLoading();
                nguoiDungDAO = new NguoiDungDAO(SignInActivity.this);

            }

            @Override
            public void onFailure(String e) {
                loading.stopLoading();
            }

            @Override
            public void onAuthNull() {
                loading.stopLoading();
            }
        });





    }






    private void initView() {
        tilUserName = findViewById(R.id.tilUserName);
        tilPassword = findViewById(R.id.tilPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void initIntentFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_LOGIN);
    }

    @NonNull
    private String getText(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString().trim();
    }

    private void loginSystem() {

        String username = getText(tilUserName);
        String password = getText(tilPassword);
        String statusLogin = ERORR;
        // Xử lý đăng nhập



        if (!username.isEmpty() && !password.isEmpty()) {


            try {
                if (UserManager.getInstance().checkUser(username, password)) {
                    // Đăng nhập thành công
                    statusLogin = SUCCESSFUL;
                    openMainActivity(username);
                } else {
                    Log.i("TAG", "loginSystem: " + username + "|" + password);
                    // Đăng nhập thất bại
                    statusLogin = FAILE;
                }
            }catch (Exception e){
                Log.i("Errror", "loginSystem: " + e);
            }
        }

        sendSatusLoginGiveMyBroadcast(statusLogin);
    }

    private void sendSatusLoginGiveMyBroadcast(String statusLogin) {
        Intent intent = new Intent();

        intent.setAction(ACTION_LOGIN);
        intent.putExtra(STATUS_LOGIN, statusLogin);

        sendBroadcast(intent);
    }

    private void openMainActivity(String username) {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.putExtra(KEY_USER, username);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private void openSignUpActivity() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        overridePendingTransition(R.anim.anim_in_right, R.anim.anim_out_left);
    }

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String statusLogin = intent.getStringExtra(STATUS_LOGIN);
            switch (statusLogin) {
                case SUCCESSFUL: // Đăng nhập thành công
                    MyToast.successful(SignInActivity.this, "Đăng nhập thành công");
                    MyNotification.getNotification(SignInActivity.this, "Đăng nhập hệ thống thành công");
                    break;
                case FAILE: // Đăng nhập thất bại
                    MyToast.error(SignInActivity.this, "Đăng nhập thất bại");
                    break;
                case ERORR: // Đăng nhập lỗi
                    MyToast.error(SignInActivity.this, "Không để trống mật khẩu hoặc tên đăng nhập");
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                loginSystem();
                break;
            case R.id.tvSignUp:
                openSignUpActivity();
                break;
        }
    }
}