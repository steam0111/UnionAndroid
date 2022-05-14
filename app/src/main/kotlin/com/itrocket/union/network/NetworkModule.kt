package com.itrocket.union.network

import com.itrocket.union.core.Prefs
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {

    val module = module {
        single {
            Retrofit.Builder()
                .baseUrl(get<String>(named(SERVER_ADDRESS_LABEL)))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
        }
        single {
            OkHttpClient()
                .newBuilder()
                .build()
        }
        single<AuthApi> {
            get<Retrofit.Builder>()
                .build()
                .create(AuthApi::class.java)
        }
        factory(named(SERVER_ADDRESS_LABEL)) {
            "${get<Prefs>().getFullServerAddress()}/api/"
        }
    }

    private const val SERVER_ADDRESS_LABEL = "serverAddress"
}