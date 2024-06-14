plugins {
    alias(libs.plugins.convertmate.android.application)
    alias(libs.plugins.convertmate.android.application.compose)
    alias(libs.plugins.convertmate.android.hilt)
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

    // Testing - Compose
    //androidTestImplementation(platform(libs.androidx.compose.bom))
    //androidTestImplementation(libs.androidx.ui.test.junit4)

    //implementation(project(":core:basedesign"))
    //implementation(projects.feature.convertor)
    //implementation(projects.core.domain)
    //implementation(projects.core.data)

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)

    // Networking
    //implementation(libs.retrofit)
    //implementation(libs.http.logging.interceptor)

    // JSON
    //implementation(libs.retrofit.gson)

    // DI - Hilt
    //implementation(libs.hilt.android)
    //ksp(libs.hilt.android.compiler)

    // Logging
    implementation(libs.timber)

    // Navigation
    implementation(libs.navigation.compose)
}