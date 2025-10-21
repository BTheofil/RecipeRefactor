import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "hu.tb.recipe.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "recipe.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "recipe.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary"){
            id = "recipe.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "recipe.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("jvmLibrary"){
            id = "recipe.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidFeatureUi"){
            id = "recipe.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
    }
}