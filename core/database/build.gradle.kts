plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek.database"

    packaging {
        resources.excludes.add("META-INF/*")
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(projects.core.common)

    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.bundles.test)

    implementation(libs.datastore.preferences)

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}