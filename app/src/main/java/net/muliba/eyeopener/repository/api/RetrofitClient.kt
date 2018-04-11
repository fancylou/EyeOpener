package net.muliba.eyeopener.repository.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by fancyLou on 10/04/2018.
 */


class RetrofitClient private constructor(context: Context) {
    private val CACHE_DIR = "eye_cache"
    private val CACHE_SIZE = 1024 * 1024 * 10L
    private val DEFAULT_TIMEOUT = 20L

    companion object {
        @Volatile
        var instance: RetrofitClient? = null

        fun getInstance(context: Context): RetrofitClient {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    if (instance == null) {
                        instance = RetrofitClient(context)
                    }
                }
            }
            return instance!!
        }

    }

    private var okHttpClient: OkHttpClient
    private var retrofit: Retrofit
    private val gson by lazy { GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create() }

    init {
        okHttpClient = OkHttpClient().newBuilder()
                .cache(cache(context))
                .addInterceptor(CacheInterceptor())
                .addNetworkInterceptor(CacheInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiService.BASE_URL)
                .build()

    }


    fun apiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    private fun cache(context: Context): Cache {
        val cacheFile = File(context.cacheDir, CACHE_DIR)
        return Cache(cacheFile, CACHE_SIZE)
    }
}