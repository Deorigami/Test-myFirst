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

// Axer HTTP recorder on non-Android targets
kotlin.sourceSets.iosMain.configure {
	dependencies {
		implementation(libs.inspektor)
	}
}

kotlin.sourceSets.jvmMain.configure {
	dependencies {
		api(libs.inspektor)
	}
}

// Chucker HTTP recorder on Android (supports copy-all feature)
kotlin.sourceSets.androidMain.configure {
	dependencies {
		implementation(libs.chucker)
	}
}

ktorfit {
	this.compilerPluginVersion.set("2.3.4")
}