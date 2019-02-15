package com.defaultxyz.skyline.domain.api

import com.defaultxyz.skyline.domain.model.Location
import com.defaultxyz.skyline.domain.model.Review
import com.defaultxyz.skyline.domain.model.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {

    @POST("/user/register")
    fun registerUser(@Body user: User): Observable<Response<User>>

    @GET("/user/login")
    fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Observable<Response<User>>

    @GET("/location/all")
    fun getLocations(): Observable<Response<List<Location>>>

    @POST("/location/new")
    fun addLocation(
        @Query("email") email: String,
        @Body addLocationModel: AddLocationModel
    ): Observable<Response<Location>>

    @GET("/review/all")
    fun getReviews(@Query("name") placeName: String): Observable<Response<List<Review>>>

    @POST("/review/new")
    fun addReview(@Body review: Review): Observable<Response<Review>>
}