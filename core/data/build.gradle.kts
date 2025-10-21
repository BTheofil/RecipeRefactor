plugins {
    alias(libs.plugins.recipe.android.library)
    alias(libs.plugins.recipe.android.room)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "hu.tb.core.data"
}

dependencies {
    implementation(libs.ktor.core)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.logging)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.ktor.json)

    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.truth)
    
    implementation(projects.core.domain)
}