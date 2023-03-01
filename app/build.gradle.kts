import dagger.hilt.android.plugin.util.capitalize

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = BuildVersion.environment.applicationId
    compileSdk = BuildVersion.environment.compileSdkVersion

    defaultConfig {
        applicationId = BuildVersion.environment.applicationId
        minSdk = BuildVersion.environment.minSdkVersion
        targetSdk = BuildVersion.environment.targetSdkVersion
        multiDexEnabled = true
        versionCode = BuildVersion.environment.appVersionCode
        versionName = BuildVersion.environment.appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] = "de.mannodermaus.junit5.AndroidJUnit5Builder"
        vectorDrawables {
            useSupportLibrary = true
        }


    }

    /* TODO: Pending set keystore signature data
    signingConfigs {
        release {
            storeFile file("keystore/MVVMProject.jks")
            storePassword "mvvmprojectpassword"
            keyAlias "MVVMProjectSignature"
            keyPassword "mvvmprojectpassword"
        }
    }
    */

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // Force copy of distributable apk to custom folder dist in root project
            val archiveBuildTypes = listOf("release", "debug")
            val distFolder = "../dist/"
            val apkNameBase = BuildVersion.environment.applicationId

            applicationVariants.filter { appVariant -> appVariant.buildType.name in archiveBuildTypes }
                .map { variant ->
                    variant.outputs.map { output ->

                        val filename = "$apkNameBase-${if (variant.versionName.isNotEmpty()) variant.versionName else "no-version"}-${output.baseName}.apk"
                        val taskSuffix = variant.name.capitalize()
                        val assembleTaskName = "assemble$taskSuffix"
                        val taskAssemble = tasks.findByName(assembleTaskName)
                        if (taskAssemble != null) {
                            val copyApkFolderTask = tasks.create(
                                name = "archive$taskSuffix",
                                type = org.gradle.api.tasks.Copy::class
                            ) {

                                description = "Archive/copy APK folder to a shared folder."
                                val sourceFolder =
                                    "$buildDir/outputs/apk/${output.baseName.replace("-", "/")}"
                                val destinationFolder =
                                    "$distFolder${output.baseName.replace("-", "/")}"

                                println("Copying APK folder from: $sourceFolder into $destinationFolder")
                                from(sourceFolder)
                                into(destinationFolder)
                                eachFile {
                                    path = filename
                                }
                                includeEmptyDirs = false
                            }

                            taskAssemble.finalizedBy(copyApkFolderTask)
                        }
                    }
                }
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

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = BuildVersion.compose.compilerVersion
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

// This tasks are for future automation console scripts
tasks.register("applicationId"){
    doLast{
        println(BuildVersion.environment.applicationId)
    }
}

tasks.register("appVersionName"){
    doLast{
        println(BuildVersion.environment.appVersionName)
    }
}


dependencies {
    implementation(libs.bundles.layer.ui)
    coreLibraryDesugaring(libs.com.android.tools.desugar.jdk.libs)

    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":model")))

    ksp(libs.bundles.compilers.ksp)

    // Testing implementation
    testImplementation(libs.bundles.testing.unit)
    testRuntimeOnly(libs.bundles.testing.unit.runtime)
    androidTestImplementation(libs.bundles.testing.android)
    androidTestRuntimeOnly(libs.bundles.testing.android.runtime)
}