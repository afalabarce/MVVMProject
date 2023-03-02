@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `version-catalog`
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.de.mannodermaus.android.junit5) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.com.github.ben.manes.versions)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
}

apply(from = "gradle-scripts/jacoco.gradle")

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
