package net.onyxmueller.pinata.files

import net.onyxmueller.pinata.PinataApiResponse
import net.onyxmueller.pinata.files.model.File
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UploadsApi {
    @Multipart
    @POST("files")
    suspend fun upload(
        @Part("name") name: RequestBody,
        @Part file: MultipartBody.Part?,
    ): PinataApiResponse<File>
}
