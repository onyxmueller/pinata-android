import java.io.FileNotFoundException
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

fun getProperty(filename: String, propName: String): String? {
    val propsFile = rootProject.file(filename)
    if (!propsFile.exists()) {
        throw FileNotFoundException("$filename does not exist!")
    }

    propsFile.inputStream().use { inputStream ->  // Resource management with 'use'
        val properties = Properties()
        properties.load(inputStream)
        return properties.getProperty(propName)  // Returns null if property not found
            ?: throw NoSuchElementException("No such property '$propName' in file '$filename'")
    }
}

android {
    namespace = "net.onyxmueller.pinataandroiddemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "net.onyxmueller.pinataandroiddemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "PINATA_JWT_TOKEN", "\"${getProperty("local.properties", "pinata_jwt_token")}\"")
        buildConfigField("String", "PINATA_GATEWAY", "\"${getProperty("local.properties", "pinata_gateway")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(project(":pinata"))
    implementation(libs.retrofit)
}