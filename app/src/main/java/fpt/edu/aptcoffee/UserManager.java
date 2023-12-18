package fpt.edu.aptcoffee;

import android.util.Log;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import fpt.edu.aptcoffee.client.ApiClient;
import fpt.edu.aptcoffee.interfaces.API;
import fpt.edu.aptcoffee.model.BanApi;
import fpt.edu.aptcoffee.model.HoaDonAPI;
import fpt.edu.aptcoffee.model.InvoiceData;
import fpt.edu.aptcoffee.model.InvoiceResponse;
import fpt.edu.aptcoffee.model.LoaiHangAPI;
import fpt.edu.aptcoffee.model.NguoiDungAPI;
import fpt.edu.aptcoffee.model.StatisticsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserManager {
    private static UserManager instance;
    private List<NguoiDungAPI> listUsers = new ArrayList<>();
    private List<HoaDonAPI> listHoaDon = new ArrayList<>();
    private List<BanApi> listBan = new ArrayList<>();

    private StatisticsResponse statisticsResponseDay;
    private StatisticsResponse statisticsResponseYear;
    private StatisticsResponse statisticsResponseMonthYear;


    private NguoiDungAPI nguoiDungAPI;


    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public List<NguoiDungAPI> getALlUsers() {
        return listUsers;
    }

    public List<BanApi> getBan() {
        return listBan;
    }

    public List<HoaDonAPI> getListHoaDon() {
        return listHoaDon;
    }

    public NguoiDungAPI getInfo() {
        return nguoiDungAPI;
    }

    public StatisticsResponse getStatisticsResponseDay() {
        return statisticsResponseDay;
    }
    public StatisticsResponse getStatisticsResponseYear() {
        return statisticsResponseYear;
    }
    public StatisticsResponse getStatisticsResponseMonthYear() {
        return statisticsResponseMonthYear;
    }


    public void callUsers(OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_GET).create(API.class);
        Call<List<NguoiDungAPI>> call = apiService.getNguoiDung();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<NguoiDungAPI>> call, @NonNull Response<List<NguoiDungAPI>> response) {
                if (response.isSuccessful()) {
                    listUsers = response.body();
                    listener.onSuccess();
                    Log.d("TAG", "onResponseList: " + listUsers.toString());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<NguoiDungAPI>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }


    public void callHoaDon(OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_GET).create(API.class);
        Call<List<HoaDonAPI>> call = apiService.getHoadon();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<HoaDonAPI>> call, @NonNull Response<List<HoaDonAPI>> response) {
                if (response.isSuccessful()) {
                    listHoaDon = response.body();
                    Log.d("__hoadon", "call: " + listHoaDon.size());
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<HoaDonAPI>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

    public void callBan(OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_GET).create(API.class);
        Call<List<BanApi>> call = apiService.getBan();

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<BanApi>> call, @NonNull Response<List<BanApi>> response) {
                if (response.isSuccessful()) {
                    listBan = response.body();
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BanApi>> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }


    public void updateBan(String id , BanApi banApi, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_PUT).create(API.class);
        Call<BanApi> call = apiService.updateTable(id,banApi);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<BanApi> call, @NonNull Response<BanApi> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BanApi> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }



    public void insertHoaDon(InvoiceData hoaDonAPI, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<InvoiceResponse> call = apiService.insertInvoice(hoaDonAPI);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<InvoiceResponse> call, @NonNull Response<InvoiceResponse> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<InvoiceResponse> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

    public void getStatisticsByMonthYear(String month, String year, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<StatisticsResponse> call = apiService.getStatisticsByMonthYear(month,year);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<StatisticsResponse> call, @NonNull Response<StatisticsResponse> response) {
                if (response.isSuccessful()) {
                    statisticsResponseMonthYear = response.body();
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatisticsResponse> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

    public void getStatisticsByYear(String year, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<StatisticsResponse> call = apiService.getStatisticsByYear(year);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<StatisticsResponse> call, @NonNull Response<StatisticsResponse> response) {
                if (response.isSuccessful()) {
                    statisticsResponseYear = response.body();
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatisticsResponse> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
    public void getStatisticsByDay(String day, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<StatisticsResponse> call = apiService.getStatisticsByDay(day);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<StatisticsResponse> call, @NonNull Response<StatisticsResponse> response) {
                if (response.isSuccessful()) {
                    statisticsResponseDay = response.body();
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatisticsResponse> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }


    public void insertType(LoaiHangAPI loaiHangAPI, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<LoaiHangAPI> call = apiService.postLoaiHang(loaiHangAPI);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<LoaiHangAPI> call, @NonNull Response<LoaiHangAPI> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoaiHangAPI> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }


    public void insertUser(NguoiDungAPI nguoiDungAPI, OnLoadInfoListener listener) {
        API apiService = ApiClient.getClient(API.BASE_POST).create(API.class);
        Call<NguoiDungAPI> call = apiService.insertUser(nguoiDungAPI);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<NguoiDungAPI> call, @NonNull Response<NguoiDungAPI> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess();
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NguoiDungAPI> call, @NonNull Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }


    public boolean checkUser(String email, String pass) {
        for (NguoiDungAPI nguoiDungAPI : listUsers) {
            if (nguoiDungAPI.getEmail().equals(email) && nguoiDungAPI.getPassword().equals(pass)) {
                this.nguoiDungAPI = nguoiDungAPI;
                return true;
            }
        }
        nguoiDungAPI = null;
        return false;
    }


    public interface OnLoadInfoListener {
        void onSuccess();

        void onFailure(String e);

        void onAuthNull();

    }

}
