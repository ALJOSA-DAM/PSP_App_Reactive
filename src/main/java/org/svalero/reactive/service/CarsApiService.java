package org.svalero.reactive.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.svalero.reactive.model.Coche;
import org.svalero.reactive.model.Usuario;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarsApiService {

    private String BASE_URL = "http://localhost:8080";
    private CarsAPI carsAPI;

    public CarsApiService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.carsAPI = retrofit.create(CarsAPI.class);
    }

    public Observable<Usuario> getUsers(String dni) {
        return this.carsAPI.getUsers(dni);
    }

    public Observable<Coche> getCars() {
        return this.carsAPI.getCars().flatMapIterable(information -> information);
    }

}
