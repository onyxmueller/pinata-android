package net.onyxmueller.pinata

import net.onyxmueller.pinata.files.model.File
import java.time.Instant
import java.util.Date

object MockUtil {

  fun mockFile() = File(
    id = "11111111-2222-3333-4444-555555555555",
    name = "image.jpg",
    cid = "AAAAAeigmtgespyq535sthcb7uj2vz7vszvx5k4tgw3k6v6nf33izjZZZZZ",
    size = 410264,
    numberOfFiles = 1,
    mimeType = "image/jpeg",
    groupId = "",
    keyValues = emptyMap(),
    createdAt = Date.from(Instant.parse("2024-12-08T22:55:04.341078Z"))
  )

  fun mockFileList() = listOf(mockFile())
}
