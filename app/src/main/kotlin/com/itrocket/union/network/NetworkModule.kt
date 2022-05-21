package com.itrocket.union.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import org.openapitools.client.apis.JwtAuthControllerApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {

    val module = module {
        single {
            Retrofit.Builder()
                .baseUrl(get<NetworkInfo>().serverAddress)
                .client(get())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
        }
        single {
            OkHttpClient()
                .newBuilder()
                .addInterceptor(get<ErrorHandlerInterceptor>())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
        single {
            ErrorHandlerInterceptor()
        }
        single<JwtAuthControllerApi> {
            get<Retrofit.Builder>()
                .build()
                .create(JwtAuthControllerApi::class.java)
        }
    }
}