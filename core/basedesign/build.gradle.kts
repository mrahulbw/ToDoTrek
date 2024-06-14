plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.library.compose)
}

android {
    namespace = "com.todotrek.basedesign"
}

dependencies {
    // Testing
    testImplementation(libs.junit)
}