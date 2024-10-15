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
        compileOnly("org.jetbrains:annotations:24.1.0")
        compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")

        implementation("com.github.cryptomorin:XSeries:11.3.0")

        val adventureVersion = "4.17.0"
        api("net.kyori:adventure-api:$adventureVersion")
        api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
        api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
        api("net.kyori:adventure-text-minimessage:$adventureVersion")
        api("net.kyori:adventure-platform-bukkit:4.3.4")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()
            options.encoding = "UTF-8"
        }
    }
}
