plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val ktlintEnabled: Boolean by rootProject.extra
val appcompatVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val navVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra
val recyclerVersion: String by rootProject.extra

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
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = javaVersionNum
        targetCompatibility = javaVersionNum
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        jvmToolchain(jvmToolchain)
    }
    namespace = "com.core.utils"
}

if (ktlintEnabled) {
    tasks.getByName<Delete>("clean").dependsOn(tasks.getByName("ktlintCheck"))
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    testImplementation("junit:junit:$junitVersion")
    implementation("androidx.recyclerview:recyclerview:$recyclerVersion")
    // glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    api("com.jakewharton.timber:timber:5.0.1")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation(project(":resources"))
    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
