plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

val ktlintEnabled: Boolean by rootProject.extra
val appcompatVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra
val gsonVersion: String by rootProject.extra
val retrofit2Version: String by rootProject.extra
val retrofitCoroutineAdapterVersion: String by rootProject.extra
val moshiVersion: String by rootProject.extra
val okhttp3Version: String by rootProject.extra
val junitVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra

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
    namespace = "com.core.network"
    buildFeatures {
        buildConfig = true
    }
}

if (ktlintEnabled) {
    tasks.getByName<Delete>("clean").dependsOn(tasks.getByName("ktlintCheck"))
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation(project(":utils"))
    implementation(project(":preference"))
    implementation("com.google.code.gson:gson:$gsonVersion")
    // Moshi
    api("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit2Version")
    api("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2Version")
    // Okhttp3
    implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")

    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
