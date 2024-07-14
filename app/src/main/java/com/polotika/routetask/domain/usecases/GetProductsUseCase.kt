package com.polotika.routetask.domain.usecases

import com.polotika.routetask.core.UseCase
import com.polotika.routetask.data.services.ApiResponse
import com.polotika.routetask.domain.entities.ProductEntity
import com.polotika.routetask.domain.repositories.IProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: IProductsRepository) : UseCase<Flow<ApiResponse<List<ProductEntity>>>, Nothing>(){
    override suspend fun invoke(params: Nothing?): Flow<ApiResponse<List<ProductEntity>>> {
        return repository.getProducts()
    }
}