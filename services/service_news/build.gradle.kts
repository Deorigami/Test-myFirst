plugins {
    id("plugin.service")
}

android.namespace = "app.tktn.service_news"

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":cores:core_service"))
        }
		commonTest.dependencies {
			implementation(libs.kotlin.test)
			implementation(libs.koin.test)
			implementation(libs.kotlinx.coroutines.test)
		}
    }
}

ktorfit {
	this.compilerPluginVersion.set("2.3.3")
}
