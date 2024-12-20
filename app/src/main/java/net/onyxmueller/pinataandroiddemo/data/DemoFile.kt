package net.onyxmueller.pinataandroiddemo.data

import net.onyxmueller.pinata.files.model.File

data class DemoFile(private val file: File) {
    var signedUrl: String? = null
    val name = file.name
    val mimeType = file.mimeType
    val createdAt = file.createdAt
}
