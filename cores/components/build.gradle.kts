plugins {
    id("plugin.feature")
}

kotlin.sourceSets.commonMain {
    dependencies {
    }
}

android.namespace = "app.tktn.components"

compose.resources {
	publicResClass = true
	packageOfResClass = "app.tktn.components"
}