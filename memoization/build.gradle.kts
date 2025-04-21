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
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit")
}

description = "Kotlin functions extensions that allows memoization."

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "Function Memoization",
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