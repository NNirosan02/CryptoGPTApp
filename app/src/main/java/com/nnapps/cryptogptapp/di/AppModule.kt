package com.nnapps.cryptogptapp.di

import android.app.Application
import android.content.Context
import com.nnapps.cryptogptapp.data.datasource.CryptoPriceDataSourceImpl
import com.nnapps.cryptogptapp.data.remote.CoinGeckoApiService
import com.nnapps.cryptogptapp.domain.datasource.CryptoPriceDataSource
import com.nnapps.cryptogptapp.domain.repository.CryptoPriceRepository
import com.nnapps.cryptogptapp.domain.repository.CryptoPriceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CoinGeckoApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideCoinGeckoApiService(retrofit: Retrofit): CoinGeckoApiService {
        return retrofit.create(CoinGeckoApiService::class.java)
    }

    @Provides
    fun provideCryptoPriceDataSource(apiService: CoinGeckoApiService): CryptoPriceDataSource {
        return CryptoPriceDataSourceImpl(apiService)
    }

    @Provides
    fun provideCryptoPriceRepository(dataSource: CryptoPriceDataSource): CryptoPriceRepository {
        return CryptoPriceRepositoryImpl(dataSource)
    }
}