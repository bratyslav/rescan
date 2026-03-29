plugins {
    id("com.bratyslav.rescan.android.library")
    alias(libs.plugins.kotlin.compose)
    id("com.bratyslav.rescan.hilt")
}

android {
    namespace = "com.bratyslav.scanner.ui"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:common"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    implementation(libs.androidx.hilt.navigation.compose)
}
