plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}
val ktlintEnabled: Boolean by rootProject.extra
val appcompatVersion: String by rootProject.extra
val coreKtxVersion: String by rootProject.extra
val constraintLayoutVersion: String by rootProject.extra
val materialVersion: String by rootProject.extra
val koinVersion: String by rootProject.extra
val navVersion: String by rootProject.extra
val roomVersion: String by rootProject.extra
val lifecycleVersion: String by rootProject.extra
val retrofit2Version: String by rootProject.extra
val retrofitCoroutineAdapterVersion: String by rootProject.extra
val moshiVersion: String by rootProject.extra
val multidexVersion: String by rootProject.extra
val gsonVersion: String by rootProject.extra
val kotlinVersion: String by rootProject.extra
val okhttp3Version: String by rootProject.extra
val scalableVersion: String by rootProject.extra
val junitTestVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val desugarVersion: String by rootProject.extra
val hiltCommonVersion: String by rootProject.extra
val hiltCompilerVersion: String by rootProject.extra
val junitVersion: String by rootProject.extra
val javaVersionNum: JavaVersion by rootProject.extra
val jvmToolchain: Int by rootProject.extra
val mockitoVersion: String by rootProject.extra

if (ktlintEnabled) {
    tasks.getByName<Delete>("clean").dependsOn(tasks.getByName("ktlintCheck"))
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.app.app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug {
            buildConfigField("String", "AppID", "\"Put AppID HERE\"")
            buildConfigField("String", "OrgID", "\"Put OrgID HERE\"")
            buildConfigField("String", "PrefName", "\"pref_\"")
            buildConfigField("String", "DatabaseName", "\"name_database\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "AppID", "\"Put AppID HERE\"")
            buildConfigField("String", "OrgID", "\"Put OrgID HERE\"")
            buildConfigField("String", "PrefName", "\"pref_\"")
            buildConfigField("String", "DatabaseName", "\"name_database\"")
        }
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = javaVersionNum
        targetCompatibility = javaVersionNum
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        jvmToolchain(jvmToolchain)
    }
    namespace = "com.app.app"
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")
    api(project(":base"))
    api(project(":data"))
    api(project(":utils"))
    api(project(":resources"))
    // navigationUI
//    implementation("android.arch.navigation:navigation-fragment:$navVersion"
    api("androidx.navigation:navigation-ui-ktx:$navVersion")
    // Dynamic Feature Module Support
    api("androidx.navigation:navigation-dynamic-features-fragment:$navVersion")
    api("androidx.multidex:multidex:$multidexVersion")
    api("com.google.code.gson:gson:$gsonVersion")
    api("com.google.android.material:material:$materialVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$junitTestVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")
    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:1.5.0")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    api("com.jakewharton.threetenabp:threetenabp:1.4.6")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugarVersion")

    // Dagger
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-common:$hiltCommonVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltCompilerVersion")
    implementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
}
