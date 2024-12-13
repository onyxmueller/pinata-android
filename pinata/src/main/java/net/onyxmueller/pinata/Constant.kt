package net.onyxmueller.pinata

object Constant {
    private const val API_VERSION = "v3"
    private const val BASE_DOMAIN = "pinata.cloud"
    const val API_URL = "https://api.$BASE_DOMAIN/${API_VERSION}/"
    const val UPLOADS_URL = "https://uploads.$BASE_DOMAIN/$API_VERSION/"
}
