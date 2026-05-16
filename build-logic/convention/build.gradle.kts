import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.cmp.template"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

gradlePlugin {
    plugins {
        register("feature"){
            id = "plugin.feature"
            implementationClass = "com.app.plugin.convention.PluginFeature"
        }
        register("service"){
            id = "plugin.service"
            implementationClass = "com.app.plugin.convention.PluginService"
        }
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    // ksp.gradlePlugin intentionally excluded — convention plugins only apply KSP by plugin ID
    // (no KSP Gradle API classes are imported), so the JAR doesn't need to be on the compile
    // classpath. Including it caused a Kotlin metadata version mismatch between KSP 2.3.x and
    // Gradle's embedded Kotlin 2.0.x used by kotlin-dsl.
//    compileOnly(libs.ksp.gradlePlugin)
//    compileOnly(libs.ktorfit.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}