package com.sathsindu.assessment2.ssl

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object CustomTLSConfiguration {
    @JvmOverloads
    fun customHttpClient(authToken: String? = null): OkHttpClient? {
        return try {
            val trustAllCerts =
                arrayOf<TrustManager>(CustomSSLValidationTrustManager())
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            OkHttpClient.Builder()
                .sslSocketFactory(
                    sslContext.socketFactory,
                    trustAllCerts[0] as X509TrustManager
                )
                .hostnameVerifier { hostname: String?, session: SSLSession? -> true }
                .addInterceptor { chain: Interceptor.Chain ->
                    val original = chain.request()
                    if (authToken != null) {
                        val requestBuilder = original.newBuilder()
                            .header("Authorization", "Bearer $authToken")
                            .method(original.method(), original.body())
                        val request = requestBuilder.build()
                        return@addInterceptor chain.proceed(request)
                    } else {
                        return@addInterceptor chain.proceed(original)
                    }
                }
                .build()
        } catch (ex: Exception) {
            null
        }
    }
}