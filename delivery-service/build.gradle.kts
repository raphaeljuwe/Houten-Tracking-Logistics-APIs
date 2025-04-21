plugins {
    id("org.springframework.boot") version "3.2.0"
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.2.0"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.typesafe:config:1.4.2") // for ConfigFactory
    implementation("io.projectreactor:reactor-core")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(project(":uid"))
}


repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
}

tasks.named<ProcessResources>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.WARN
    from(sourceSets.main.get().resources.srcDirs) {
        exclude("**/application.properties")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set("com.houtengamentee.kotlin.delivery.DeliveryServiceApplication")
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    mainClass.set("com.houtengamentee.kotlin.delivery.DeliveryServiceApplication")
}
