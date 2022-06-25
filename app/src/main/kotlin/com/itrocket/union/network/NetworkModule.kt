package com.itrocket.union.network

import com.itrocket.token_auth.Authenticator
import com.itrocket.token_auth.TokenInterceptor
import com.itrocket.token_auth.TokenManager
import com.itrocket.union.authMain.domain.TokenManagerImpl
import com.itrocket.union.serverConnect.domain.dependencies.ServerConnectRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.openapitools.client.custom_api.AccountingObjectApi
import org.openapitools.client.custom_api.AuthApi
import org.openapitools.client.custom_api.BranchesApi
import org.openapitools.client.custom_api.CounterpartyApi
import org.openapitools.client.custom_api.DepartmentApi
import org.openapitools.client.custom_api.EmployeeApi
import org.openapitools.client.custom_api.EquipmentTypeApi
import org.openapitools.client.custom_api.LocationApi
import org.openapitools.client.custom_api.NomenclaturesApi
import org.openapitools.client.custom_api.OrganizationApi
import org.openapitools.client.custom_api.ProducerApi
import org.openapitools.client.custom_api.RegionApi
import org.openapitools.client.infrastructure.BigDecimalAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkModule {

    private val AUTHORIZED_CLIENT_QUALIFIER = named("authorizedClient")
    private val AUTHORIZED_RETROFIT_QUALIFIER = named("authorizedRetrofit")

    private val UNAUTHORIZED_CLIENT_QUALIFIER = named("unauthorizedClient")
    private val UNAUTHORIZED_RETROFIT_QUALIFIER = named("unauthorizedRetrofit")

    val module = module {
        single { Moshi.Builder().add(BigDecimalAdapter()).build() }
        single<Retrofit>(UNAUTHORIZED_RETROFIT_QUALIFIER) {
            Retrofit.Builder()
                .baseUrl(get<ServerConnectRepository>().getReadyServerUrl())
                .client(get(UNAUTHORIZED_CLIENT_QUALIFIER))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(get()))
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
                .baseUrl(get<ServerConnectRepository>().getReadyServerUrl())
                .client(get(AUTHORIZED_CLIENT_QUALIFIER))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(get()))
                .build()
        }
        single(AUTHORIZED_CLIENT_QUALIFIER) {
            OkHttpClient()
                .newBuilder()
                .authenticator(get<Authenticator>())
                .addInterceptor(get<ErrorHandlerInterceptor>())
                .addInterceptor(get<TokenInterceptor>())
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
        single {
            ErrorHandlerInterceptor()
        }
        single<TokenManager> {
            TokenManagerImpl(get())
        }
        single {
            TokenInterceptor(get())
        }
        single {
            Authenticator(get())
        }
        single<AuthApi> {
            get<Retrofit>(UNAUTHORIZED_RETROFIT_QUALIFIER)
                .create(AuthApi::class.java)
        }
        single<NomenclaturesApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(NomenclaturesApi::class.java)
        }
        single<OrganizationApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(OrganizationApi::class.java)
        }
        single<DepartmentApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(DepartmentApi::class.java)
        }
        single<EmployeeApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(EmployeeApi::class.java)
        }
        single<ProducerApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(ProducerApi::class.java)
        }
        single<CounterpartyApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(CounterpartyApi::class.java)
        }
        single<RegionApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(RegionApi::class.java)
        }
        single<BranchesApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(BranchesApi::class.java)
        }
        single<AccountingObjectApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(AccountingObjectApi::class.java)
        }
        single {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(LocationApi::class.java)
        }
        single<EquipmentTypeApi> {
            get<Retrofit>(AUTHORIZED_RETROFIT_QUALIFIER)
                .create(EquipmentTypeApi::class.java)
        }
    }
}