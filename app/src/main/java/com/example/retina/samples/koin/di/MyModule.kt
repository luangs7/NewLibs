package com.example.retina.samples.koin.di

import com.example.retina.samples.main.model.UnitConverterFactory
import com.example.retina.samples.main.retrofit.ApiInterface
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private val BASE_URL:String = "https://jsonplaceholder.typicode.com/"

val remoteModule = applicationContext {
    bean { loggInterception()  }
    bean { provideGson() }
    bean { provideHttpClient(get() as HttpLoggingInterceptor) }
    bean { provideRetrofit(get() as Gson,get() as OkHttpClient) }
    bean { provideApi(get() as Retrofit) }
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

internal fun loggInterception():HttpLoggingInterceptor{
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
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