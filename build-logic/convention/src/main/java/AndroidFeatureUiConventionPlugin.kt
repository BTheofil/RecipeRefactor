import hu.tb.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply("recipe.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {
                "implementation"(project.libs.findBundle("koin").get())
                "implementation"(project.libs.findBundle("compose").get())
                "debugImplementation"(project.libs.findBundle("compose.debug").get())
            }
        }
    }
}