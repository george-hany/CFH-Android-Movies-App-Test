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
val roomVersion: String by rootProject.extra
val retrofit2Version: String by rootProject.extra
val gsonVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val hiltCommonVersion: String by rootProject.extra
val hiltCompilerVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val retrofitCoroutineAdapterVersion: String by rootProject.extra
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
            buildConfigField("String", "BASE_URL", "\"http://api.themoviedb.org/3/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", "\"http://api.themoviedb.org/3/\"")
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
    namespace = "com.core.data"
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
    implementation("androidx.room:room-runtime:$roomVersion")
//    implementation("androidx.room:room-coroutines:$version_room_coroutine"
    implementation("androidx.room:room-rxjava2:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    // Retrofit2
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:$retrofitCoroutineAdapterVersion")
    api(project(":utils"))
    api(project(":network"))
    api(project(":preference"))
    implementation("com.google.code.gson:gson:$gsonVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-common:$hiltCommonVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
    implementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")
}
