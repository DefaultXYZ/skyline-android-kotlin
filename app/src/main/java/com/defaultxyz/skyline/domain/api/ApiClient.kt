package com.defaultxyz.skyline.domain.api

import com.defaultxyz.skyline.domain.model.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {

    @POST("/user/register")
    fun registerUser(): Observable<Response<Any>>

    @GET("/user/login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Observable<Response<User>>

    @GET("/location/all")
    fun getLocations(): Observable<Response<List<Any>>>

    @POST("/location/new")
    fun addLocation(
        @Query("email") email: String,
        @Body location: Any
    ): Observable<Response<Any>>

    @GET("/review/all")
    fun getReviews(@Query("name") placeName: String): Observable<Response<List<Any>>>

    @POST("/review/new")
    fun addReview(@Body review: Any): Observable<Response<Any>>
}