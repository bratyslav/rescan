plugins {
    id("com.bratyslav.rescan.android.library")
    id("com.bratyslav.rescan.hilt")
}

android {
    namespace = "com.bratyslav.data"
}

dependencies {
    implementation(project(":features:auth:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.javax.inject)
}