package net.onyxmueller.pinata

sealed class PinataApiResponse<T : Any> {
    class Success<T : Any>(val data: T) : PinataApiResponse<T>()

    class Error<T : Any>(val code: Int, val message: String?) : PinataApiResponse<T>()

    class Exception<T : Any>(val e: Throwable) : PinataApiResponse<T>()
}

suspend fun <T : Any> PinataApiResponse<T>.onSuccess(
    executable: suspend (T) -> Unit,
): PinataApiResponse<T> =
    apply {
        if (this is PinataApiResponse.Success<T>) {
            executable(data)
        }
    }

suspend fun <T : Any> PinataApiResponse<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit,
): PinataApiResponse<T> =
    apply {
        if (this is PinataApiResponse.Error<T>) {
            executable(code, message)
        }
    }

suspend fun <T : Any> PinataApiResponse<T>.onException(
    executable: suspend (e: Throwable) -> Unit,
): PinataApiResponse<T> =
    apply {
        if (this is PinataApiResponse.Exception<T>) {
            executable(e)
        }
    }
