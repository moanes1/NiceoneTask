package com.moanes.datasource
import com.moanes.datasource.network.Service
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    val httpLoggingInterceptor: HttpLoggingInterceptor
        @Provides
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    @Provides
    fun getApiInterface(retroFit: Retrofit):
            Service {
        return retroFit.create(Service::class.java)
    }


    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient):
            Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    fun getOkHttpCleint(httpLoggingInterceptor: HttpLoggingInterceptor):
            OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


}