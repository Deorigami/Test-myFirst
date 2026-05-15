plugins {
    id("plugin.feature")
}
android.namespace = "com.cmp.template.feature.sample"
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":services:service_sample"))
            implementation(project(":cores:core_feature"))
            implementation(project(":cores:core_service"))
            implementation(project(":cores:core_navigation"))
            implementation(project(":cores:components"))
        }
    }
}
