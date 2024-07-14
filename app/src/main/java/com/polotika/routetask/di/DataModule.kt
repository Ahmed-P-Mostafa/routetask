package com.polotika.routetask.di

import com.polotika.routetask.data.repositories.ProductsRepository
import com.polotika.routetask.data.services.WebService
import com.polotika.routetask.domain.repositories.IProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideProductsRepository(webService: WebService):IProductsRepository{
        return ProductsRepository(webService)
    }

}