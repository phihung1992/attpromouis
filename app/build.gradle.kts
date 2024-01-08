plugins {
    id("com.android.application")
}

android {
    namespace = "com.attsolution.promouis"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.attsolution.promouis"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation(project(":promouis"))
}