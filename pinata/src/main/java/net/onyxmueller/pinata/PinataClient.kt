package net.onyxmueller.pinata

import android.net.Uri
import com.google.gson.GsonBuilder
import net.onyxmueller.pinata.Constant.API_URL
import net.onyxmueller.pinata.Constant.UPLOADS_URL
import net.onyxmueller.pinata.adapters.ItemTypeAdapterFactory
import net.onyxmueller.pinata.adapters.PinataApiResponseCallAdapterFactory
import net.onyxmueller.pinata.files.FileApiRequestHelper
import net.onyxmueller.pinata.files.FilesApi
import net.onyxmueller.pinata.files.Order
import net.onyxmueller.pinata.files.UploadsApi
import net.onyxmueller.pinata.files.model.File
import net.onyxmueller.pinata.files.model.SignData
import net.onyxmueller.pinata.files.model.UpdateData
import net.onyxmueller.pinata.interceptors.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PinataClient internal constructor(jwtToken: String, gatewayUrl: String) {
    @Volatile
    private var filesInstance: Files? = null

    private fun createRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val gson =
            GsonBuilder()
                .registerTypeAdapterFactory(ItemTypeAdapterFactory())
                .create()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(PinataApiResponseCallAdapterFactory.create())
            .build()
    }

    private val getFilesApi: FilesApi by lazy {
        createRetrofit(
            API_URL,
            OkHttpClient()
                .newBuilder()
                .addInterceptor(AuthInterceptor(jwtToken))
                .build(),
        ).create(FilesApi::class.java)
    }

    private val getUploadsApi: UploadsApi by lazy {
        createRetrofit(
            UPLOADS_URL,
            OkHttpClient()
                .newBuilder()
                .addInterceptor(AuthInterceptor(jwtToken, true))
                .build(),
        ).create(UploadsApi::class.java)
    }

    companion object {
        @Volatile
        private var instance: PinataClient? = null

        fun get(jwtToken: String, gatewayUrl: String): PinataClient =
            instance ?: synchronized(this) {
                instance ?: PinataClient(jwtToken, gatewayUrl).also { instance = it }
            }
    }

    val files: Files by lazy {
        filesInstance ?: synchronized(this) {
            filesInstance ?: Files(getFilesApi, getUploadsApi, gatewayUrl).also {
                filesInstance = it
            }
        }
    }

    class Files internal constructor(
        private val filesApi: FilesApi,
        private val uploadsApi: UploadsApi,
        private val gatewayUrl: String,
    ) {
        suspend fun list(
            name: String? = null,
            group: String? = null,
            mineType: String? = null,
            cid: String? = null,
            cidPending: Boolean? = null,
            limit: Int? = null,
            order: Order? = null,
            pageToken: String? = null,
        ) = filesApi.list(
            name = name,
            group = group,
            mimeType = mineType,
            cid = cid,
            cidPending = cidPending,
            // TODO: handle metadata filtering
            limit = limit,
            order = order,
            pageToken = pageToken,
        )

        suspend fun get(id: String) = filesApi.get(id)

        suspend fun sign(
            cid: String,
            expires: Int,
            date: Long = System.currentTimeMillis(),
            method: String = "GET",
        ) =
            filesApi.sign(
                SignData(
                    url = "https://$gatewayUrl/files/$cid",
                    expires = expires,
                    date = date,
                    method = method,
                ),
            )

        suspend fun upload(file: java.io.File, customName: String? = null): PinataApiResponse<File> {
            val fileName = if (customName.isNullOrEmpty()) file.name else customName
            return uploadsApi.upload(
                FileApiRequestHelper.toRequestBody(fileName),
                FileApiRequestHelper.prepareFilePart("file", Uri.fromFile(file)),
            )
        }

        suspend fun update(id: String, name: String? = null, keyvalues: Map<String, Any>? = null) =
            filesApi.update(id, UpdateData(name, keyvalues))

        suspend fun delete(id: String) = filesApi.delete(id)
    }
}
