plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.lucaaa.template"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc", "spigot-api", "1.19.4-R0.1-SNAPSHOT")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    shadowJar {
        minimize()
    }
}