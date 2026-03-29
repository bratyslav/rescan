// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false // com.android.application
    alias(libs.plugins.kotlin.android) apply false // org.jetbrains.kotlin.android
    alias(libs.plugins.kotlin.compose) apply false // org.jetbrains.kotlin.plugin.compose
    alias(libs.plugins.ksp) apply false // com.google.devtools.ksp
    alias(libs.plugins.hilt) apply false // com.google.dagger.hilt.android
    alias(libs.plugins.gms) apply false // com.google.gms.google-services
}