plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.library.compose)
}

android {
    namespace = "com.todotrek.basedesign"
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(projects.core.common)

    androidTestImplementation(libs.bundles.android.test)
}