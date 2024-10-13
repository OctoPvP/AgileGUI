plugins {
    `maven-publish`
    signing
    id("io.freefair.lombok") version "8.10.2"
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("com.mojang:authlib:1.5.25")
}

val javaComponent: SoftwareComponent = components["java"]

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn.add(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(javaComponent)
                artifact(sourcesJar)
                artifact(javadocJar)
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("Agile GUI")
                    description.set("Library for easy creation of GUIs for Bukkit plugins.")
                    url.set("https://github.com/OctoPvP/AgileGUI")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("http://www.opensource.org/licenses/mit-license.php")
                        }
                    }

                    developers {
                        developer {
                            id.set("badbird5907")
                            name.set("Evan Yu")
                            organization.set("OctoMC")
                            organizationUrl.set("https://github.com/OctoPvP")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/OctoPvP/AgileGUI.git")
                        developerConnection.set("scm:git:ssh://github.com:OctoPvP/AgileGUI.git")
                        url.set("https://github.com/OctoPvP/AgileGUI")
                    }
                }
            }
        }

        repositories {
            repositories {
                maven ("https://repo.octopvp.net/public"){
                    name = "octomc"
                    credentials(PasswordCredentials::class)
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
        }

    }

    signing {
        /*useGpgCmd()
        val signingKey = System.getenv("GPG_KEY")
        val signingPassword = System.getenv("GPG_PASS")
        val secretKey = System.getenv("GPG_SECRET_KEY")
        useInMemoryPgpKeys(signingKey, secretKey, signingPassword)*/
        sign(publishing.publications["maven"])
    }
}
