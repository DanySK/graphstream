plugins {
    `java-library`
    `maven-publish`
    signing
    id("org.danilopianini.publish-on-central")
    id("org.danilopianini.git-sensitive-semantic-versioning")
}

allprojects {
    group = "org.danilopianini"
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "org.danilopianini.publish-on-central")
    apply(plugin = "signing")

    version = rootProject.version

    repositories {
        mavenCentral()
    }

    dependencies {
        testImplementation("junit:junit:_")
    }

    sourceSets {
        main {
            java {
                setSrcDirs(listOf("src"))
            }
            resources {
                setSrcDirs(listOf("src"))
            }
        }
        test {
            java {
                setSrcDirs(listOf("src-test"))
            }
            resources {
                setSrcDirs(listOf("src-test"))
            }
        }
    }

    tasks.test {
        ignoreFailures = true // Not my job to make them work
        testLogging {
            showExceptions = true
            showCauses = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }

    tasks.javadoc {
        isFailOnError = false
    }

    signing {
        if (System.getenv("CI") == "true") {
            val signingKey: String? by project
            val signingPassword: String? by project
            useInMemoryPgpKeys(signingKey, signingPassword)
        }
    }

    publishOnCentral {
        licenseName = "LGPL3"
        licenseUrl = "http://www.gnu.org/copyleft/lesser.html"
        projectUrl = "http://graphstream-project.org"
    }

    publishing {
        publications {
            withType<MavenPublication> {
                pom {
                    developers {
                        developer {
                            name.set("The GraphStream Team")
                        }
                    }
                }
            }
        }
    }

}

tasks.register("publishSubprojects") {
    subprojects {
        tasks.filter { it.name.contains("publish", ignoreCase = true) }
            .forEach { this@register.dependsOn(it) }
    }
}
