plugins {
    alias(libs.plugins.recipe.android.feature.ui)
}

android {
    namespace = "hu.tb.shop.presentation"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(projects.core.presentation)
    implementation(projects.core.domain)
}