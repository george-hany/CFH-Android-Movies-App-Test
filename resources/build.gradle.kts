plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val appcompatVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val scalableVersion: String by rootProject.extra
val multidexVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra

android {
    compileSdk = 34

    defaultConfig {
        multiDexEnabled = true
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
    namespace = "com.core.resources"
    compileOptions {
        sourceCompatibility = javaVersionNum
        targetCompatibility = javaVersionNum
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        jvmToolchain(jvmToolchain)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

    api("com.intuit.sdp:sdp-android:$scalableVersion")
    api("com.intuit.ssp:ssp-android:$scalableVersion")
    implementation("androidx.multidex:multidex:$multidexVersion")
    implementation("com.google.android.material:material:$materialVersion")

    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
