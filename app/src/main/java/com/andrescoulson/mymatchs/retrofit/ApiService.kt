package com.andrescoulson.mymatchs.retrofit

import com.andrescoulson.mymatchs.config.Endpoints
import com.andrescoulson.mymatchs.model.Match
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("results.json")
    fun getResults(): Observable<List<Match>?>

    @GET("fixtures.json")
    fun getFixture(): Observable<List<Match>?>
}