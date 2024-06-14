import convention.implementation
import convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("hilt.android").get())
                implementation(libs.findLibrary("hilt.navigation.compose").get())
                "ksp"(libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}