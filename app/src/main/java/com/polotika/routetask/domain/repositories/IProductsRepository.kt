package com.polotika.routetask.domain.repositories

import com.polotika.routetask.data.services.ApiResponse
import com.polotika.routetask.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    suspend fun getProducts():Flow<ApiResponse<List<ProductEntity>>>
}