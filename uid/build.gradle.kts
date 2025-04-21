plugins {
    kotlin("jvm") version "2.1.20"
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
    kotlin("plugin.serialization") version "2.1.20"
}

group = "com.tomtom.kotlin"
version = "1.8.6"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.0"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.typesafe:config:1.4.2") // for ConfigFactory
    implementation("io.projectreactor:reactor-core")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:3.16.1")
}

java {
    withJavadocJar()
    withSourcesJar()
}

tasks.jar {
    archiveBaseName.set("uid")
    manifest {
        attributes["Implementation-Title"] = "Uid"
        attributes["Implementation-Version"] = archiveVersion
    }
    enabled = true // Ensure the jar task is enabled
}

tasks.bootJar {
    enabled = false // Disable the bootJar task
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