plugins {
    id("java-library")
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}

subprojects {

    apply {
        plugin("java-library")
    }

    group = "dev.octomc"
    version = "1.4.0"

    dependencies {
        compileOnly("org.jetbrains:annotations:26.0.1")
        compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

        val adventureVersion = "4.17.0"
        compileOnly("net.kyori:adventure-api:$adventureVersion")
        compileOnly("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
        compileOnly("net.kyori:adventure-text-serializer-gson:$adventureVersion")
        compileOnly("net.kyori:adventure-text-minimessage:$adventureVersion")
        compileOnly("net.kyori:adventure-platform-bukkit:4.3.3")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()
            options.encoding = "UTF-8"
        }
    }
}
