package com.itrocket.union.network

import com.itrocket.token_auth.Authenticator
import com.itrocket.token_auth.TokenInterceptor
import com.itrocket.token_auth.TokenManager
import com.itrocket.union.authMain.domain.TokenManagerImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.openapitools.client.apis.JwtAuthControllerApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {

    val AUTHORIZED_CLIENT_QUALIFIER = named("authorizedClient")
    val AUTHORIZED_RETROFIT_QUALIFIER = named("authorizedRetrofit")

    val UNAUTHORIZED_CLIENT_QUALIFIER = named("unauthorizedClient")
    val UNAUTHORIZED_RETROFIT_QUALIFIER = named("unauthorizedRetrofit")

    val module = module {
        single<Retrofit>(UNAUTHORIZED_RETROFIT_QUALIFIER) {
            Retrofit.Builder()
                .baseUrl(get<NetworkInfo>().serverAddress)
                .client(get(UNAUTHORIZED_CLIENT_QUALIFIER))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        single(UNAUTHORIZED_CLIENT_QUALIFIER) {
            OkHttpClient()
                .newBuilder()
                .addInterceptor(get<ErrorHandlerInterceptor>())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
        single<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER) {
            Retrofit.Builder()
                .baseUrl(get<NetworkInfo>().serverAddress)
                .client(get(AUTHORIZED_CLIENT_QUALIFIER))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        single(AUTHORIZED_CLIENT_QUALIFIER) {
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
        single<TokenManager> {
            TokenManagerImpl(get(), get())
        }
        single {
            TokenInterceptor(get())
        }
        single {
            Authenticator(get())
        }
        single<JwtAuthControllerApi> {
            get<Retrofit>(UNAUTHORIZED_RETROFIT_QUALIFIER)
                .create(JwtAuthControllerApi::class.java)
        }
    }
}