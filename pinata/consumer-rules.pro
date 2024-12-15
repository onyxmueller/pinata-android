# R8 full mode strips signatures from non-kept items.
-keep,allowobfuscation,allowshrinking class net.onyxmueller.pinata.PinataClient { *; }
-keep,allowobfuscation,allowshrinking class net.onyxmueller.pinata.PinataApiResponse