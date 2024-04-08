package com.example.asm_md18309;

import com.example.asm_md18309.Model.CarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search-distributor")
    Call<List<CarModel>> searchDistributor(@Query("key") String key);
    String DOMAIN = "http://10.24.13.175:3000/";
    @GET("/api/list")
    Call<List<CarModel>> getCars();
    @POST("/add_xe")
    Call<CarModel> addCar(@Body CarModel carModel);
    @DELETE("/xoa/:id")
    Call<Void> deleteCar(@Path("id") String carId);
}
