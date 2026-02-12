rootProject.name = "Attendees-Check-App"

pluginManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content { 
              	includeGroupByRegex("com\\.android.*")
              	includeGroupByRegex("com\\.google.*")
              	includeGroupByRegex("androidx.*")
              	includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
    }
}
plugins {
    //https://github.com/JetBrains/compose-hot-reload?tab=readme-ov-file#set-up-automatic-provisioning-of-the-jetbrains-runtime-jbr-via-gradle
    id("org.gradle.toolchains.foojay-resolver-convention").version("0.10.0")
}

include(
    ":composeApp",
    ":features:feature_auth",
	":features:feature_feed",
    ":features:feature_util",
    ":features:feature_explore",
    ":features:feature_profile",
    ":features:feature_news",
    ":services:service_feed",
    ":services:service_news",
    ":cores:core_feature",
    ":cores:components",
    ":cores:core_service",
)
includeBuild("build-logic")

