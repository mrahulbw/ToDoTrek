import convention.implementation

plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek.data"
}

dependencies {
    // Testing
    testImplementation(libs.bundles.test)
    testImplementation(projects.core.common)

    androidTestImplementation(libs.bundles.android.test)

    // Logging
    implementation(libs.timber)

    implementation(project(":core:domain"))
    implementation(project(":core:database"))
}