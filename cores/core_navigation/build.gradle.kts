plugins {
	id("plugin.service")
}

android.namespace = "com.cmp.template.core_navigation"

kotlin {
	sourceSets {
		commonMain.dependencies {
			// No dependencies — GlobalNavigation is a pure interface
		}
	}
}

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}