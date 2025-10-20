plugins {
    alias(libs.plugins.recipe.android.application)
    alias(libs.plugins.recipe.android.application.compose)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "hu.tb.reciperefactor"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation)

    implementation(libs.koin)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.shop.presentation)
    implementation(projects.recipe.presentation)
    implementation(projects.depo.presentation)
}