plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek.domain"
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(projects.core.common)

    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.androidx.core.ktx)
}