package com.polotika.routetask.data.repositories

import com.polotika.routetask.data.services.ApiResponse
import com.polotika.routetask.data.services.WebService
import com.polotika.routetask.domain.entities.ProductEntity
import com.polotika.routetask.domain.repositories.IProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val webService: WebService) : IProductsRepository {

    override suspend fun getProducts(): Flow<ApiResponse<List<ProductEntity>>> {
        return flow {
           emit( ApiResponse.Loading)
            val response = webService.getProducts()
            try{
                if (!response.isSuccessful)
                    emit(ApiResponse.Failure(response.message()))
                else {
                    val products = response.body()?.products?.map { it.toEntity() } ?: emptyList()
                    emit(ApiResponse.Success(products))
                }
            }
            catch (e:UnknownHostException){
                emit(ApiResponse.Failure("No Internet Connection"))
            }
            catch (e:Exception){
                emit(ApiResponse.Failure(e.localizedMessage))
            }
        }
    }
}