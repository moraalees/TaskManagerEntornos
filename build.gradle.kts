plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.20"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("org.jetbrains.dokka") version "1.8.10"
}

group = "es.prog2425.taskmanager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:5.8.1")
    testImplementation("io.kotest:kotest-assertions-core:5.8.1")
    testImplementation("io.kotest:kotest-framework-engine:5.8.1")
    testImplementation("io.kotest:kotest-framework-api:5.8.1")
    testImplementation("io.mockk:mockk:1.13.10")
}

tasks.test {
    useJUnitPlatform()
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka"))
}

kotlin {
    jvmToolchain(17)
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
}
