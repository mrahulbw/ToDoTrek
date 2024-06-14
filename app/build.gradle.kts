plugins {
    alias(libs.plugins.todotrek.android.application)
    alias(libs.plugins.todotrek.android.application.compose)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek"

    defaultConfig {
        applicationId = "com.todotrek"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.basedesign)
    implementation(projects.core.domain)
    implementation(projects.core.data)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)

    // Logging
    implementation(libs.timber)

    // Navigation
    implementation(libs.navigation.compose)
}