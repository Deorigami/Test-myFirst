plugins {
    id("plugin.feature")
}
android.namespace = "com.cmp.template.feature.auth"
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":services:service_auth"))
            implementation(project(":cores:core_feature"))
            implementation(project(":cores:core_service"))
            implementation(project(":cores:core_navigation"))
            implementation(project(":cores:components"))

            implementation(libs.rinku)
            implementation(libs.rinku.compose.ext)
        }
        androidMain.dependencies {
            implementation(libs.androidx.browser)
        }
    }
}

