package net.muliba.eyeopener.repository.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by fancyLou on 10/04/2018.
 */


class CacheInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        var request = chain?.request()
        val response = chain?.proceed(request)
        // read from cache for 60 s
        val maxAge = 60
        val cacheControl = request?.cacheControl().toString()
            Log.e("CacheInterceptor", "6s load cahe" + cacheControl)
        return response?.newBuilder()
                ?.removeHeader("Pragma")
                ?.removeHeader("Cache-Control")
                ?.header("Cache-Control", "public, max-age=" + maxAge)
                ?.build()
    }
}