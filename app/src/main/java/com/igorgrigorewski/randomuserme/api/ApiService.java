package com.igorgrigorewski.randomuserme.api;

import com.igorgrigorewski.randomuserme.content.RandomUserMe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/api/?results=13")
    Call<RandomUserMe> getRandomUsers();
}
