plugins {
	id("plugin.service")
}

android.namespace = "app.tktn.core_navigation"

kotlin {
	sourceSets {
		commonMain.dependencies {
			implementation(project(":services:service_news"))
		}
	}
}

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}