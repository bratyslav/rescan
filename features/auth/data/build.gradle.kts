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
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.play.services)

    // Firebase
    implementation(platform(libs.firebase.bom))
    // Firebase Auth
    implementation(libs.firebase.auth)
    // Firebase Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
}