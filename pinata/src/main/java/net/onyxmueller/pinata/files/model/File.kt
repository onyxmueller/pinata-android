package net.onyxmueller.pinata.files.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class File(
    var id: String,
    var name: String,
    var cid: String,
    var size: Int = 0,
    @SerializedName("number_of_files")
    var numberOfFiles: Int = 0,
    @SerializedName("mime_type")
    var mimeType: String,
    @SerializedName("group_id")
    var groupId: String,
    @SerializedName("keyvalues")
    var keyValues: Map<String, Any>,
    @SerializedName("created_at")
    var createdAt: Date,
)
