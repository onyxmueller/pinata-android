package net.onyxmueller.pinata

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class PinataApiResponseCall<T : Any>(
    private val proxy: Call<T>,
) : Call<PinataApiResponse<T>> {
    override fun enqueue(callback: Callback<PinataApiResponse<T>>) {
        proxy.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val networkResult = handleApi { response }
                    callback.onResponse(this@PinataApiResponseCall, Response.success(networkResult))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    val networkResult = PinataApiResponse.Exception<T>(t)
                    callback.onResponse(this@PinataApiResponseCall, Response.success(networkResult))
                }
            },
        )
    }

    override fun execute(): Response<PinataApiResponse<T>> = throw NotImplementedError()

    override fun clone(): Call<PinataApiResponse<T>> = PinataApiResponseCall(proxy.clone())

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun cancel() {
        proxy.cancel()
    }

    fun <T : Any> handleApi(
        execute: () -> Response<T>,
    ): PinataApiResponse<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                PinataApiResponse.Success(body)
            } else {
                PinataApiResponse.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            PinataApiResponse.Error(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            PinataApiResponse.Exception(e)
        }
    }
}
