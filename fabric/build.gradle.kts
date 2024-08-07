import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
}

dependencies {
    implementation(project(":agile-gui"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }
}