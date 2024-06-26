import org.gradle.api.Plugin
import org.gradle.api.Project
import convention.implementation
import convention.libs
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("todotrek.android.library")
                apply("todotrek.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation(libs.findLibrary("navigation-compose").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
                implementation(libs.findLibrary("lifecycle-viewModel-ktx").get())
                implementation(libs.findLibrary("lifecycle-viewModel-compose").get())
                implementation(libs.findLibrary("lifecycle-runtime-compose").get())
                implementation(project(":core:common"))
                implementation(project(":core:domain"))
                implementation(project(":core:basedesign"))

                implementation(libs.findLibrary("timber").get())
            }
        }
    }
}