pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ReScan"
include(":app")
include(":core")
include(":features")
include(":features:auth")
include(":features:auth:ui")
include(":features:auth:domain")
include(":features:auth:data")
include(":features:scanner")
include(":features:scanner:ui")
include(":core:network")
include(":core:database")
include(":core:ui")
include(":core:common")
