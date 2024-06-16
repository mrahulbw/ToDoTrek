import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import convention.configureAndroidApplication
import convention.configureKotlinAndroid
import convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)
                configureAndroidApplication(this)
            }

            dependencies {
                implementation(project(":core:common"))
            }
        }
    }
}