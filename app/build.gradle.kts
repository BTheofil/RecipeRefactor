plugins {
    alias(libs.plugins.recipe.android.application.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "hu.tb.reciperefactor"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.navigation)

    implementation(libs.koin)

    implementation(libs.kotlinx.serialization.json)

    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.shopping.presentation)
    implementation(projects.recipe.presentation)
    implementation(projects.depo.presentation)
}