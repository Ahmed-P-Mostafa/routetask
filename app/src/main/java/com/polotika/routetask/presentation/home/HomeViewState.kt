package com.polotika.routetask.presentation.home

import com.polotika.routetask.data.models.ProductModel
import com.polotika.routetask.data.responses.ProductResponse
import com.polotika.routetask.domain.entities.ProductEntity

sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Success(val products: List<ProductEntity>) : HomeViewState()
    data class Error(val message: String) : HomeViewState()
}