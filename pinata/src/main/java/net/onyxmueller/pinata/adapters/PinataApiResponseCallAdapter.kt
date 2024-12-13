package net.onyxmueller.pinata.adapters

import net.onyxmueller.pinata.PinataApiResponse
import net.onyxmueller.pinata.PinataApiResponseCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class PinataApiResponseCallAdapter(
    private val resultType: Type,
) : CallAdapter<Type, Call<PinataApiResponse<Type>>> {
    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<PinataApiResponse<Type>> {
        return PinataApiResponseCall(call)
    }
}
