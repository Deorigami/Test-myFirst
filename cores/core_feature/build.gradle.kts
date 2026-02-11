import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("plugin.feature")
}

kotlin {
	sourceSets.commonMain {

		dependencies {
			implementation(project(":cores:core_service"))
		}
	}
}

android.namespace = "app.tktn.core_feature"
