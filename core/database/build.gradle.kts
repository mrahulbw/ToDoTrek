plugins {
    alias(libs.plugins.todotrek.android.library)
    alias(libs.plugins.todotrek.android.hilt)
}

android {
    namespace = "com.todotrek.database"
}

dependencies {
    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.datastore.preferences)

    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}