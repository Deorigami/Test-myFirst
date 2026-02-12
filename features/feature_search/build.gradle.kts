plugins {
    id("plugin.feature")
}

android.namespace = "app.tktn.feature_search"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":services:service_news"))
            implementation(project(":cores:core_feature"))
            implementation(project(":cores:core_service"))
        }
    }
}
