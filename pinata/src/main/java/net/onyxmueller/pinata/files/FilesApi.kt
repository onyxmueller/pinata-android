package net.onyxmueller.pinata.files

import net.onyxmueller.pinata.PinataApiResponse
import net.onyxmueller.pinata.files.model.File
import net.onyxmueller.pinata.files.model.ListResponse
import net.onyxmueller.pinata.files.model.SignData
import net.onyxmueller.pinata.files.model.UpdateData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FilesApi {
    @GET("files")
    suspend fun list(
        @Query("name") name: String? = null,
        @Query("group") group: String? = null,
        @Query("mimeType") mimeType: String? = null,
        @Query("cid") cid: String? = null,
        @Query("cidPending") cidPending: Boolean? = null,
        @Query("limit") limit: Int? = null,
        @Query("order") order: Order? = null,
        @Query("pageToken") pageToken: String? = null,
    ): PinataApiResponse<ListResponse>

    @GET("files/{id}")
    suspend fun get(
        @Path("id") id: String,
    ): PinataApiResponse<File>

    @POST("files/sign")
    suspend fun sign(
        @Body signData: SignData,
    ): PinataApiResponse<String>

    @PUT("files/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body updateData: UpdateData,
    ): PinataApiResponse<File>

    @DELETE("files/{id}")
    suspend fun delete(
        @Path("id") id: String,
    ): PinataApiResponse<Unit>
}
