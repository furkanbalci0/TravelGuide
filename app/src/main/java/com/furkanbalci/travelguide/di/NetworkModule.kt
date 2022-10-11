package com.furkanbalci.travelguide.di

import com.furkanbalci.travelguide.BuildConfig
import com.furkanbalci.travelguide.data.datasource.mock.MockApiService
import com.furkanbalci.travelguide.data.datasource.triposo.TriposoApiService
import com.furkanbalci.travelguide.util.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideChain(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Triposo-Account", BuildConfig.TRIPOSO_ACCOUNT_ID)
                .addHeader("X-Triposo-Token", BuildConfig.TRIPOSO_API_KEY)
                .build()
            chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(moshi: Moshi): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())

    @MockApi
    @Provides
    @Singleton
    fun provideMockRetrofit(builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(Constants.MOCK_BASE_URL).build()
    }

    @TriposoApi
    @Provides
    @Singleton
    fun provideTriposoRetrofit(chain: OkHttpClient, builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(Constants.TRIPOSO_BASE_URL).client(chain).build()
    }

    @MockApi
    @Provides
    @Singleton
    fun provideMockService(@MockApi retrofit: Retrofit): MockApiService {
        return retrofit.create(MockApiService::class.java)
    }

    @TriposoApi
    @Provides
    @Singleton
    fun provideTriposoService(@TriposoApi retrofit: Retrofit): TriposoApiService {
        return retrofit.create(TriposoApiService::class.java)
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MockApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TriposoApi
}