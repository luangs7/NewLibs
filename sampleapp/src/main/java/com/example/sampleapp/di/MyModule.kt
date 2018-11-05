package com.example.retina.samples.koin.di

import android.os.Build
import com.example.retina.samples.main.model.UnitConverterFactory
import com.example.retina.samples.main.retrofit.ApiInterface
import com.example.sampleapp.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private val BASE_URL: String = "http://192.168.1.34/PHP/"

val networkModule = module {
    single { loggInterception() }
    single { provideGson() }
    single { provideHttpClient(get() as HttpLoggingInterceptor) }
    single { provideRetrofit(get() as Gson, get() as OkHttpClient) }
    single { provideApi(get() as Retrofit) }
}


internal fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

internal fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

    return OkHttpClient().newBuilder()
            .connectTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
            .readTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout((30000 * 6).toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
}

internal fun loggInterception(): HttpLoggingInterceptor {
    val logInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    else logInterceptor.level = HttpLoggingInterceptor.Level.NONE

    return logInterceptor
}


internal fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return gsonBuilder.create()
}

internal fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(UnitConverterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()
}