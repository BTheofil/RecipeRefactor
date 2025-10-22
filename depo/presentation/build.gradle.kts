plugins {
    alias(libs.plugins.recipe.android.feature.ui)
}

android {
    namespace = "hu.tb.depo.presentation"
}

dependencies {
    implementation(projects.core.presentation)
    implementation(projects.core.domain)
}