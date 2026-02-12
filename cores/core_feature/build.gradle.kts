import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("plugin.feature")
}

kotlin {
	sourceSets.commonMain {

		dependencies {
			implementation(project(":cores:core_service"))
			api(project(":cores:core_navigation"))
			implementation(libs.connectivity.core)
			implementation(libs.androidx.lifecycle.viewmodel)
			implementation(libs.kotlinx.coroutines.core)
			implementation(libs.koin.annotations)
			implementation(libs.koin.core)
		}
	}
}

android.namespace = "app.tktn.core_feature"
