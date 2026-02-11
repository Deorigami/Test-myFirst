plugins {
    id("plugin.feature")
}

kotlin.sourceSets.commonMain {
    dependencies {
        implementation(project(":cores:core_feature"))
        implementation(project(":services:service_feed"))
        implementation(libs.composeMediaPlayer)
    }
}

android.namespace = "app.tktn.feature_feed"