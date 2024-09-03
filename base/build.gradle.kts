plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}
val ktlintEnabled: Boolean by rootProject.extra
val appcompatVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val navVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra

if (ktlintEnabled) {
    tasks.getByName<Delete>("clean").dependsOn(tasks.getByName("ktlintCheck"))
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
    namespace = "com.core.base"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // view model
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // navigationUI
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Dynamic Feature Module Support
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    implementation(project(":utils"))
    implementation(project(":data"))
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
