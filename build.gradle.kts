import java.util.Calendar.YEAR

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    // hilt
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12"
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    //    spotless and ktlint
    id("org.jlleitschuh.gradle.ktlint") version "11.6.0"
    id("com.diffplug.spotless") version "6.21.0"
}

spotless {
    kotlin {
        // by default the target is every '.kt' and '.kts` file in the java sourcesets
//４つのうちどれかを選ぶ
//        ktfmt()    // has its own section below
        ktlint() // has its own section below
//        diktat()   // has its own section below
//        prettier() // has its own section below
        licenseHeader("/* (C) ${YEAR} */") // or licenseHeaderFile
    }
    kotlinGradle {
        target("*.gradle.kts", "app/src/main/java/com/example/inventory/**") // default target for kotlinGradle
        ktlint() // or ktfmt() or prettier()
    }
}