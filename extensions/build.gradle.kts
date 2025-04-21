plugins {
    kotlin("jvm") version "2.1.20"
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
}

group = "com.tomtom.kotlin"
version = "1.8.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("io.mockk:mockk-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
}

description = "General-purpose extension functions that can make your code more concise and readable."

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "Extensions",
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