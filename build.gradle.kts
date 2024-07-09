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
    version = "1.3.0"

    dependencies {
        compileOnly("org.jetbrains:annotations:21.0.1")
        compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")

        implementation("com.github.cryptomorin:XSeries:11.2.0")

        val adventureVersion = "4.17.0"
        api("net.kyori:adventure-api:$adventureVersion")
        api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
        api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
        api("net.kyori:adventure-platform-bukkit:4.3.3")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = JavaVersion.VERSION_1_8.toString()
            targetCompatibility = JavaVersion.VERSION_1_8.toString()
            options.encoding = "UTF-8"
        }
    }
}
