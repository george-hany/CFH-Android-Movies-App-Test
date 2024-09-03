pluginManagement {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "Src"
include(":app")
include(":base")
include(":data")
include(":utils")
include(":network")
include(":preference")
include(":resources")
