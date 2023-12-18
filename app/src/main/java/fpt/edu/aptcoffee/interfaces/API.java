package fpt.edu.aptcoffee.interfaces;

import java.util.List;

import fpt.edu.aptcoffee.model.BanApi;
import fpt.edu.aptcoffee.model.HoaDonAPI;
import fpt.edu.aptcoffee.model.InvoiceData;
import fpt.edu.aptcoffee.model.InvoiceResponse;
import fpt.edu.aptcoffee.model.LoaiHangAPI;
import fpt.edu.aptcoffee.model.NguoiDungAPI;
import fpt.edu.aptcoffee.model.SanPhamAPi;
import fpt.edu.aptcoffee.model.StatisticsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    String BASE_GET = "http://172.16.54.150:3000/api/";
    String BASE_POST = "http://172.16.54.150:3000/";
    String BASE_PUT = "http://172.16.54.150:3000/";

    @POST("insert/user")
    Call<NguoiDungAPI> insertUser(@Body NguoiDungAPI nguoiDungAPI);


    @POST("/insert/invoice")
    Call<InvoiceResponse> insertInvoice(@Body InvoiceData invoiceData);

    @GET("types")
    Call<List<LoaiHangAPI>> getLoaiHang();


    @GET("invoices")
    Call<List<HoaDonAPI>> getHoadon();

    @GET("users")
    Call<List<NguoiDungAPI>> getNguoiDung();

    //    http://localhost:3000/api/products
    @GET("products")
    Call<List<SanPhamAPi>> getSanPham();

    @POST("insert/type")
    Call<LoaiHangAPI> postLoaiHang(@Body LoaiHangAPI api); // Add parentheses here


    @GET("tables")
    Call<List<BanApi>> getBan();

    @PUT("/update/table/{id}")
    Call<BanApi> updateTable(@Path("id") String id, @Body BanApi banApi);

    // http://localhost:3000/statistics/byday/:date
    @GET("/statistics/byday/{day}")
    Call<StatisticsResponse> getStatisticsByDay(@Path("day") String date);

    @GET("/statistics/bymonth/{month}/{year}")
    Call<StatisticsResponse> getStatisticsByMonthYear(@Path("month") String day, @Path("year") String year);

    @GET("/statistics/byyear/{year}")
    Call<StatisticsResponse> getStatisticsByYear(@Path("year") String year);
}
