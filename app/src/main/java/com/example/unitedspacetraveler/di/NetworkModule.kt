package com.example.unitedspacetraveler.di

import com.example.unitedspacetraveler.BuildConfig
import com.example.unitedspacetraveler.network.ServiceInterface
import com.example.unitedspacetraveler.utils.BASE_URL
import com.example.unitedspacetraveler.utils.CustomHttpLogger
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkModule {

    fun service(): ServiceInterface {
        val logging = HttpLoggingInterceptor(CustomHttpLogger())
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(1, TimeUnit.MINUTES)
        httpClient.readTimeout(1, TimeUnit.MINUTES)
        httpClient.callTimeout(1, TimeUnit.MINUTES)
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()

            val requestBuilder: Request.Builder = original.newBuilder()
            requestBuilder.url(url.build())
            chain.proceed(requestBuilder.build())
        }

        val clt = httpClient.build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(clt)
            .build()

        return retrofit.create(ServiceInterface::class.java)
    }
}