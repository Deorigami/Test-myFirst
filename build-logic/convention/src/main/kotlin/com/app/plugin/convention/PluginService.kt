package com.app.plugin.convention

import androidx.room.gradle.RoomExtension
import com.android.build.gradle.LibraryExtension
import de.jensklingenberg.ktorfit.gradle.KtorfitPluginExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.withType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import kotlin.text.set

class PluginService : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
    override fun apply(target: Project) {
        with(target) {
            plugins.apply {
                apply(libs.findPlugin("multiplatform").get().get().pluginId)
                apply(libs.findPlugin("android.library").get().get().pluginId)
                apply(libs.findPlugin("ktorfit").get().get().pluginId)
                apply(libs.findPlugin("kotlinx.serialization").get().get().pluginId)
                apply(libs.findPlugin("ksp").get().get().pluginId)
                apply(libs.findPlugin("room").get().get().pluginId)
            }

            extensions.configure<KotlinMultiplatformExtension> {
				compilerOptions {
					freeCompilerArgs.set(listOf("-Xcontext-parameters"))
				}
                androidTarget {
                    //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
                    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
                }

                jvm()

                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach {
                    it.binaries.framework {
                        baseName = "ComposeApp"
                        isStatic = true
                    }
                }

                sourceSets.apply {
                    commonMain.configure {
                        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                        dependencies {
                            implementation(libs.findLibrary("kermit").get())
                            implementation(libs.findLibrary("kotlinx.coroutines.core").get())
                            implementation(libs.findLibrary("ktor.client.core").get())
                            implementation(libs.findLibrary("ktor.client.content.negotiation").get())
                            implementation(libs.findLibrary("ktor.client.serialization").get())
                            implementation(libs.findLibrary("ktor.serialization.json").get())
                            implementation(libs.findLibrary("ktor.client.logging").get())
                            implementation(libs.findLibrary("kotlinx.serialization.json").get())
                            implementation(libs.findLibrary("multiplatformSettings").get())
                            implementation(libs.findLibrary("kotlinx.datetime").get())
                            implementation(libs.findLibrary("ktorfit").get())
                            implementation(libs.findLibrary("ktorfit.converter").get())
                            implementation(libs.findLibrary("ktorfit.converter.call").get())
                            implementation(libs.findLibrary("ktorfit.converter.flow").get())
                            implementation(project.dependencies.platform(libs.findLibrary("koin.bom").get()))
                            implementation(project.dependencies.platform(libs.findLibrary("koin.annotations.bom").get()))
                            implementation(libs.findLibrary("koin.core").get())
                            implementation(libs.findLibrary("koin.annotations").get())
                            implementation(libs.findLibrary("room.runtime").get())
                            implementation(libs.findLibrary("room.sqlite").get())
                        }
                    }
                    androidMain.dependencies {
                        implementation(libs.findLibrary("kotlinx.coroutines.android").get())
                        implementation(libs.findLibrary("ktor.client.okhttp").get())
                    }
                    jvmMain.dependencies {
                        implementation(libs.findLibrary("kotlinx.coroutines.swing").get())
                        implementation(libs.findLibrary("ktor.client.okhttp").get())
                    }
                    iosMain.dependencies {
                        implementation(libs.findLibrary("ktor.client.darwin").get())
                    }
                }
            }
			extensions.configure<KtorfitPluginExtension> {
				kotlinVersion.set("2.3.3")
			}
			extensions.configure<RoomExtension>(){
				schemaDirectory("$projectDir/schemas")
			}
            extensions.configure<LibraryExtension> {
                compileSdk = 36
                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                sourceSets.getByName("main").apply {
                    manifest.srcFile("src/androidMain/AndroidManifest.xml")
                    res.srcDirs("src/androidMain/res")
                }
                buildFeatures {
                    //enables a Compose tooling support in the AndroidStudio
//                    this.compose = true
                }
                buildTypes {
                    getByName("debug") {

                    }
                }
            }
            tasks.withType<KotlinCompilationTask<*>>().configureEach {
                if (name != "kspCommonMainKotlinMetadata") {
                    dependsOn("kspCommonMainKotlinMetadata")
                }
            }
            afterEvaluate {
                tasks.filter {
                    it.name.contains("SourcesJar", true)
                }.forEach {
                    println("SourceJarTask====>${it.name}")
                    it.dependsOn("kspCommonMainKotlinMetadata")
                }
            }
            dependencies {
                add("kspCommonMainMetadata", libs.findLibrary("koin.annotations.ksp").get())
                add("kspCommonMainMetadata", libs.findLibrary("ktorfit.ksp").get())
            }
            configurations.configureEach {
                exclude("androidx.window.core", "window-core")
            }
        }
    }
}