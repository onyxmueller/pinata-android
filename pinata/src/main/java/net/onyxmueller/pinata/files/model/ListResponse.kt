package net.onyxmueller.pinata.files.model

import com.google.gson.annotations.SerializedName

data class ListResponse(
    val files: List<File>,
    @SerializedName("next_page_token")
    val nextPageToken: String
)
