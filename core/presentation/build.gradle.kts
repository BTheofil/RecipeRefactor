plugins {
    alias(libs.plugins.recipe.android.library.compose)
}

android {
    namespace = "hu.tb.core.presentation"
}

dependencies {
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material3)

    implementation(projects.core.domain)
}