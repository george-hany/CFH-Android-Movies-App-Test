

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.1" apply false
    id("com.android.library") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
    id("androidx.navigation.safeargs") version "2.5.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
apply(plugin = "org.jlleitschuh.gradle.ktlint")

val ktlintEnabled by extra { true }
val appcompatVersion by extra { "1.6.1" }
val coreKtxVersion by extra { "1.12.0" }
val constraintLayoutVersion by extra { "2.1.4" }
val materialVersion by extra { "1.11.0" }
val koinVersion by extra { "2.0.1" }
val navVersion by extra { "2.7.7" }
val roomVersion by extra { "2.6.1" }
val lifecycleVersion by extra { "2.7.0" }
val retrofit2Version by extra { "2.9.0" }
val retrofitCoroutineAdapterVersion by extra { "0.9.2" }
val moshiVersion by extra { "1.15.0" }
val multidexVersion by extra { "2.0.1" }
val gsonVersion by extra { "2.10.1" }
val kotlinVersion by extra { "1.9.0" }
val okhttp3Version by extra { "4.11.0" }
val scalableVersion by extra { "1.1.0" }
val junitTestVersion by extra { "1.1.5" }
val espressoVersion by extra { "3.5.1" }
val hiltVersion by extra { "2.48" }
val desugarVersion by extra { "2.0.3" }
val hiltCommonVersion by extra { "1.2.0" }
val hiltCompilerVersion by extra { "1.0.0" }
val junitVersion by extra { "4.13.2" }
val recyclerVersion by extra { "1.3.2" }
val dataStoreVersion by extra { "1.1.0-beta02" }
val mockitoVersion by extra { "5.6.0" }
val javaVersionNum by extra { JavaVersion.VERSION_17 }
val jvmToolchain by extra { 17 }

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version = "0.47.1"
    debug = true
    verbose = true
    android = true
    outputToConsole = true
    ignoreFailures = true
    enableExperimentalRules = true
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
