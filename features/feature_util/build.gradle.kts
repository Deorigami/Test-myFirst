plugins {
    id("plugin.feature")
}

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(project(":cores:core_feature"))
        implementation(project(":services:service_news"))
        implementation(libs.kotlinx.datetime)
    }
}

android.namespace = "app.tktn.feature_dashboard"