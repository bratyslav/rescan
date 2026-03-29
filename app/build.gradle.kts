plugins {
    id("com.bratyslav.rescan.android.application")
    id("com.bratyslav.rescan.hilt")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.bratyslav.rescan"
    defaultConfig {
        // Must match `package_name` in app/google-services.json (overrides convention default if needed)
        applicationId = "com.bratyslav.rescan"
    }
}

dependencies {
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(project(":features:auth:ui"))
    implementation(project(":features:auth:domain"))
    implementation(project(":features:auth:data"))
    implementation(project(":features:scanner:ui"))

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}