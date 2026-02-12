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

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}
