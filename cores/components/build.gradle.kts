plugins {
    id("plugin.feature")
}

kotlin.sourceSets.commonMain {
    dependencies {
    }
}

android.namespace = "com.cmp.template.components"

compose.resources {
	publicResClass = true
	packageOfResClass = "com.cmp.template.components"
}