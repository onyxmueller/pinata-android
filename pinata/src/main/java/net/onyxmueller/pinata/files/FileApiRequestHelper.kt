package net.onyxmueller.pinata.files

import android.net.Uri
import android.webkit.MimeTypeMap
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object FileApiRequestHelper {

    fun toRequestBody(value: String): RequestBody {
        val mediaType = "text/plain".toMediaType()
        return value.toRequestBody(mediaType)
    }

    fun prepareFilePart(partName: String, uriFile: Uri?): MultipartBody.Part? {
        val file = File(uriFile?.path ?: "")
        return if (file.exists()){
            val mime = getMimeType(uriFile.toString())
            if (mime != null) {
                val requestFile =  file.asRequestBody(mime.toMediaTypeOrNull())
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part.createFormData(partName, file.name, requestFile)
            }else{
                return null
            }
        } else {
            null
        }
    }

    fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            val mime = MimeTypeMap.getSingleton()
            type = mime.getMimeTypeFromExtension(extension)
        }
        return type
    }

}