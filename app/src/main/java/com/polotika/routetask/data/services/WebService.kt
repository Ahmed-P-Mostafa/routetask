package com.polotika.routetask.data.services

import com.polotika.routetask.data.responses.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("products")
    suspend fun getProducts():Response<ProductResponse>
}