package com.example.asm_md18309;

import com.example.asm_md18309.Model.CarModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String DOMAIN = "http://192.168.6.102:3000/";
    @GET("/api/list")
    Call<List<CarModel>> getCars();
    @POST("/add_xe")
    Call<CarModel> addCar(@Body CarModel carModel);
    @DELETE("/xoa/:id")
    Call<Void> deleteCar(@Path("id") String carId);
}
