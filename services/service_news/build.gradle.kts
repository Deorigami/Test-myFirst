plugins {
    id("plugin.service")
}

android.namespace = "app.tktn.service_news"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":cores:core_service"))
        }
    }
}
