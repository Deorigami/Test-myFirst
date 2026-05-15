import org.gradle.kotlin.dsl.ktorfit

plugins {
	id("plugin.service")
}

android.namespace = "com.cmp.template.core_service"

kotlin.sourceSets.commonMain.configure {
	dependencies {
		implementation(libs.kotlinx.datetime)
		implementation(libs.kermit)
	}
}

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}