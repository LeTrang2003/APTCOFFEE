package fpt.edu.aptcoffee.client;

import fpt.edu.aptcoffee.interfaces.API;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
