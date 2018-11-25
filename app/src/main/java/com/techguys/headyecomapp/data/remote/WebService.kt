package com.techguys.headyecomapp.data.remote

import com.techguys.headyecomapp.data.remote.models.ECommerceDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface WebService{

    @GET("/json")
    fun getEcommerceData(): Call<ECommerceDataResponse>
}