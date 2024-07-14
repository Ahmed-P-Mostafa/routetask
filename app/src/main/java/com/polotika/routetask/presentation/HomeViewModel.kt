package com.polotika.routetask.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polotika.routetask.data.services.ApiResponse
import com.polotika.routetask.domain.usecases.GetProductsUseCase
import com.polotika.routetask.presentation.home.HomeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetProductsUseCase) :ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val viewState: StateFlow<HomeViewState> = _viewState.asStateFlow()

    val query = mutableStateOf("")
    fun onSearch(text:String){}

    fun onQueryChanged(query:String){
        this.query.value = query
    }
    fun getProducts() {
        viewModelScope.launch {
            useCase().collect{ result->
                when(result){
                    ApiResponse.Empty -> {}
                    is ApiResponse.Failure -> {
                        _viewState.value = HomeViewState.Error(result.errorMessage)
                    }
                    ApiResponse.Loading -> {
                        _viewState.value = HomeViewState.Loading
                    }
                    is ApiResponse.Success -> {
                        _viewState.value = HomeViewState.Success(result.data?: emptyList())
                    }
                }
            }
        }
    }
    init {
        getProducts()
    }
}