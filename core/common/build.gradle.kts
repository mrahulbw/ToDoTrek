plugins {
    alias(libs.plugins.todotrek.android.library)
}

android {
    namespace = "com.todotrek.common"
}

dependencies {
    testImplementation(libs.turbine.testing)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.android.arch.core.testing)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.io.mockk)
}