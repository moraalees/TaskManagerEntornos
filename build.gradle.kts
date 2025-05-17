plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.22"
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    id("org.jetbrains.dokka") version "1.8.10"
}

group = "es.prog2425.taskmanager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

detekt {
    buildUponDefaultConfig = true // usa configuración base de Detekt
    allRules = false              // activa solo las reglas recomendadas
}

val kotestVersion = "5.5.4"
val mockKVersion = "1.13.4"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockKVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("dokka"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}



