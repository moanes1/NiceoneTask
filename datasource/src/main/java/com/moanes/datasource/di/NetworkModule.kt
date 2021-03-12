package com.moanes.datasource.di

import com.moanes.datasource.BuildConfig
import com.moanes.datasource.network.Service
import com.moanes.datasource.repositories.CharactersRepo
import com.moanes.datasource.repositories.CharactersRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    val httpLoggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    @Provides
    @Singleton
    fun getApiInterface(retroFit: Retrofit):
            Service {
        return retroFit.create(Service::class.java)
    }


    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient):
            Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor):
            OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideCharactersRepo(service: Service): CharactersRepo {
        return CharactersRepoImpl(service)
    }
}