@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.de.mannodermaus.android.junit5)
}

android {
    namespace = "${BuildVersion.environment.applicationId}.datasource"
    compileSdk = BuildVersion.environment.compileSdkVersion

    defaultConfig {
        minSdk = BuildVersion.environment.minSdkVersion
        targetSdk = BuildVersion.environment.targetSdkVersion
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        //consumerProguardFiles = "consumer-rules.pro"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        isCoreLibraryDesugaringEnabled = true

    }
    kotlinOptions {
        jvmTarget = "11"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

    // Needed for testing usage
    testOptions {
        animationsDisabled=true
        reportDir = "$rootDir/instrumentedTestsResults/reports/$project.name"
        resultsDir = "$rootDir/instrumentedTestsResults/results/$project.name"
        unitTests{
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all { test ->
                test.useJUnitPlatform()
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.layer.datasource)
    coreLibraryDesugaring(libs.com.android.tools.desugar.jdk.libs)
    implementation(project(mapOf("path" to ":model")))

    ksp(libs.bundles.compilers.ksp)
    kapt(libs.bundles.compilers.kapt.room)

    // Testing implementation
    testImplementation(libs.bundles.testing.unit)
    testRuntimeOnly(libs.bundles.testing.unit.runtime)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestRuntimeOnly(libs.bundles.testing.android.runtime)
}