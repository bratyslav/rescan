plugins {
    id("com.bratyslav.rescan.android.library")
}

android {
    namespace = "com.bratyslav.scanner"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
}
