import org.gradle.kotlin.dsl.ktorfit

plugins {
	id("plugin.service")
}

android.namespace = "app.tktn.core_service"

kotlin.sourceSets.commonMain.configure {
	dependencies {
		implementation(libs.inspektor)
	}
}

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}