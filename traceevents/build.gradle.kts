
plugins {
    id("org.springframework.boot") version "3.2.0"
    kotlin("jvm") version "2.1.20"
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
    kotlin("plugin.serialization") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"

}

group = "com.tomtom.kotlin"
version = "1.8.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation("nl.jqno.equalsverifier:equalsverifier")
    testImplementation("org.mockito:mockito-core")
    testImplementation("io.mockk:mockk-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
}

description = "Trace events offer a flexible way of logging semantic events during the run of an application."

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "Trace Events",
            "Implementation-Version" to version,
            "Built-By" to "TomTom (http://tomtom.com)",
            "License" to "Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0)"
        )
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
    }
}

license {
    header = rootProject.file("LICENSE.txt")
    strictCheck = false
    mapping("kt", "SLASHSTAR_STYLE")
    mapping("java", "SLASHSTAR_STYLE")
    include("**/*.kt")
    include("**/*.java")
}

kotlin {
    jvmToolchain(21)
}