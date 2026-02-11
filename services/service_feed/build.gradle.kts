plugins {
    id("plugin.service")
}

kotlin.sourceSets.commonMain.configure {
	dependencies {
		api(project(":cores:core_service"))
	}
}

android.namespace = "app.tktn.service_feed"