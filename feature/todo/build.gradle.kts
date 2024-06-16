plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.library.compose)
    alias(libs.plugins.todotrek.android.feature)
}

android {
    namespace = "com.todotrek"
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(projects.core.common)

    androidTestImplementation(libs.bundles.android.test)
}