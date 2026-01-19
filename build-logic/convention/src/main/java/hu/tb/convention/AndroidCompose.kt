package hu.tb.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    extension: ApplicationExtension
) {
    extension.run {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
            "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
        }
    }
}

internal fun Project.configureAndroidCompose(
    extension: LibraryExtension
) {
    extension.run {
        buildFeatures {
            compose = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "implementation"(libs.findLibrary("androidx.ui.tooling.preview").get())
            "debugImplementation"(libs.findLibrary("androidx.ui.tooling").get())
        }
    }
}