plugins {
	id("plugin.service")
}

android.namespace = "app.tktn.core_service"

kotlin.sourceSets.commonMain.configure {
	dependencies {
		implementation(libs.inspektor)
	}
}