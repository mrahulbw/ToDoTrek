plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.library.compose)
    alias(libs.plugins.todotrek.android.feature)
}

android {
    namespace = "com.todotrek.convertor"
}

dependencies {
    testImplementation(libs.turbine.testing)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.android.arch.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.io.mockk)
}