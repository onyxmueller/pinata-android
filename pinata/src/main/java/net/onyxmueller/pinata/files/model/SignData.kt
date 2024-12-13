package net.onyxmueller.pinata.files.model

data class SignData(
    val url: String,
    val expires: Int,
    val date: Long,
    val method: String,
)
