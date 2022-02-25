plugins {
    kotlin("jvm") version "1.6.10"
    id("com.diffplug.spotless") version "6.2.2"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.snowbldr:janky-http:0.0.2")
    implementation("org.web3j:core:4.9.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
}

spotless {
    kotlin {
        targetExclude("**/generated/**")
        ktlint()
    }
}

application {
    mainClass.set("MainKt")
}