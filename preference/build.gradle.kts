plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
val ktlintEnabled: Boolean by rootProject.extra
val appcompatVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra
val dataStoreVersion: String by rootProject.extra

android {
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersionNum
        targetCompatibility = javaVersionNum
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        jvmToolchain(jvmToolchain)
    }
    namespace = "com.core.prefrence"
}

if (ktlintEnabled) {
    tasks.getByName<Delete>("clean").dependsOn(tasks.getByName("ktlintCheck"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    api("androidx.datastore:datastore-preferences:$dataStoreVersion")

    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
