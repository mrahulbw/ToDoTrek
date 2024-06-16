plugins {
    alias(libs.plugins.todotrek.android.library)
}

android {
    namespace = "com.todotrek.common"
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)

    androidTestImplementation(libs.bundles.android.test)
}