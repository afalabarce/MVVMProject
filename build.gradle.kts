buildscript {

    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${BuildVersion.dependencyInjection.hiltVersion}")
        classpath("com.google.gms:google-services:${BuildVersion.devTools.googleServicesVersion}")
    }
}

plugins {
    id ("com.android.application") version BuildVersion.devTools.gradleVersion apply false
    id ("com.android.library") version BuildVersion.devTools.gradleVersion apply false
    id ("org.jetbrains.kotlin.android") version BuildVersion.devTools.kotlinVersion apply false
    id ("com.google.devtools.ksp") version BuildVersion.devTools.kspVersion apply false
    id ("de.mannodermaus.android-junit5") version BuildVersion.testing.mannodermausVersion apply false
    id("org.jetbrains.kotlin.jvm") version BuildVersion.devTools.kotlinVersion apply false
    id("com.github.ben-manes.versions") version BuildVersion.gradleCatalog.versionId
    id("nl.littlerobots.version-catalog-update") version BuildVersion.gradleCatalog.littleRobotsVersion
}

apply(from = "gradle-scripts/jacoco.gradle")

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
