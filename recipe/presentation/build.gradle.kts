plugins {
    alias(libs.plugins.recipe.android.feature.ui)
}

android {
    namespace = "hu.tb.recipe.presentation"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.presentation)
    implementation(projects.core.domain)
}