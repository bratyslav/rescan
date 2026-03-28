package com.bratyslav.rescan

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", "com.google.dagger:hilt-android:2.56.1")
                add("ksp", "com.google.dagger:hilt-compiler:2.56.1")
            }
        }
    }
}