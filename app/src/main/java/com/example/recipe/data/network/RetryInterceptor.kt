package com.example.recipe.data.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 *  Интерсептор для отправки повторного запроса при возникновении 429 ошибки (Too Many Requests)
 */
class RetryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        while (response.code == 429) {
            // retry the request
            response.close()
            response = chain.proceed(request)
        }
        return response
    }
}