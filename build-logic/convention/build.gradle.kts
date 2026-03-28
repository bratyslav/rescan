plugins {
    `kotlin-dsl`
}

group = "com.myapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.9.3")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.56.1")
}

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
        register("hilt") {
            id = "com.bratyslav.rescan.hilt"
            implementationClass = "com.bratyslav.rescan.HiltConventionPlugin"
        }
    }
}