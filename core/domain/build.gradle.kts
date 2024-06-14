plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek.domain"
}

dependencies {
    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.android.arch.core.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.core.ktx)
}