plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.spotless)
    alias(libs.plugins.nexus.plugin)
}

mavenPublishing {
    val artifactId = "pinata"
    coordinates(
        "net.onyxmueller.pinata",
        artifactId,
        "1.0.1",
    )

    pom {
        name.set("Pinata")
        description.set(
            "The Pinata library provides convenient access to the Pinata API for Kotlin and " +
                "Android applications.",
        )
        inceptionYear.set("2024")
        url.set("https://github.com/onyxmueller/pinata-android/")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("onyx")
                name.set("Onyx Mueller")
                url.set("https://onyxmueller.net")
            }
        }
        scm {
            url.set("https://github.com/onyxmueller/pinata-android/")
            connection.set("scm:git:git://github.com/onyxmueller/pinata-android.git")
            developerConnection.set("scm:git:ssh://git@github.com/onyxmueller/pinata-android.git")
        }
    }
}

android {
    namespace = "net.onyxmueller.pinata"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Need this to mock Uri.fromFile(...)
    // https://developer.android.com/training/testing/local-tests#error
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // unit test
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mock.webserver)
    testImplementation(libs.coroutines.test)
}
