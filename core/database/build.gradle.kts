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
    testImplementation(libs.junit)
    testImplementation(libs.turbine.testing)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.android.arch.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.io.mockk)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.turbine.testing)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.io.mockk)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.datastore.preferences)

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}