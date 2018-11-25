package com.techguys.headyecomapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.techguys.headyecomapp.data.local.HeadyEcomDatabase
import com.techguys.headyecomapp.data.remote.WebService

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val app: Application ){

    private val baseUrl = "https://stark-spire-93433.herokuapp.com"

    @Provides
    @Singleton
    fun provideAppContext(): Application {
        return app
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit): WebService {
        return retrofit.create<WebService>(WebService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): HeadyEcomDatabase {
        return HeadyEcomDatabase.getInstance(context)
        //return null
    }

    @Provides
    @Singleton
    fun provideSharedPreference(context: Application): SharedPreferences{
        return context.getSharedPreferences("HeadyEcomPreference", Context.MODE_PRIVATE)
    }
}