plugins {
    id("plugin.feature")
}

android.namespace = "app.tktn.headlines"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":services:service_news"))
            implementation(project(":cores:core_feature"))
            implementation(project(":cores:core_service"))
            implementation(project(":cores:core_navigation"))
            implementation(project(":cores:components"))
        }
    }
}
