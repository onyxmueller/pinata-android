package net.onyxmueller.pinata

object Constant {
    private const val API_VERSION = "v3"
    private const val BASE_DOMAIN = "pinata.cloud"
    const val API_BASE_URL = "https://api.$BASE_DOMAIN/"
    const val API_URL = "$API_BASE_URL/${API_VERSION}/"
    const val UPLOADS_URL = "https://uploads.$BASE_DOMAIN/$API_VERSION/"
}
