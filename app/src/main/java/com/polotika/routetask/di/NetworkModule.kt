package com.polotika.routetask.di

import androidx.core.os.BuildCompat
import com.google.gson.internal.GsonBuildConfig
import com.polotika.routetask.BuildConfig
import com.polotika.routetask.core.CONNECT_TIMEOUT
import com.polotika.routetask.core.READ_TIMEOUT
import com.polotika.routetask.core.WRITE_TIMEOUT
import com.polotika.routetask.data.services.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun okHttpClientProvider(
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logger)
            }
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }.build()


    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            //  .addCallAdapterFactory()
            .build()
    }

    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit):WebService{
        return retrofit.create(WebService::class.java)
    }

}