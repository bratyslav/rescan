plugins {
    `kotlin-dsl`
}

group = "com.myapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Gives your plugin access to the android{} / kotlin{} DSL
    compileOnly("com.android.tools.build:gradle:8.9.3")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
}

// Register your convention plugins
gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "com.bratyslav.rescan.android.library"
            implementationClass = "com.bratyslav.rescan.AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "com.bratyslav.rescan.android.application"
            implementationClass = "com.bratyslav.rescan.AndroidApplicationConventionPlugin"
        }
    }
}