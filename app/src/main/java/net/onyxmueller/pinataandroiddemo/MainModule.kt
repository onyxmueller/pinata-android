package net.onyxmueller.pinataandroiddemo

import net.onyxmueller.pinata.PinataClient

object MainModule {
    val pinataClient: PinataClient =
        PinataClient.get(
            BuildConfig.PINATA_JWT_TOKEN,
            BuildConfig.PINATA_GATEWAY,
        )
}
