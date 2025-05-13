plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "2.0.20" // opcional pero útil
}

group = "es.prog2425.taskmanager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotestVersion = "5.5.4"
val mockKVersion = "1.13.4"

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockKVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion") // solo esta del runner
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}