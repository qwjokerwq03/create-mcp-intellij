plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.intellij") version "1.15.0"
}

group = "com.qwjokerwq03"
version = "1.1.0"

repositories {
    mavenCentral()
}

// Configure IntelliJ platform integration
intellij {
    version.set("2023.2")
    type.set("IC") // IntelliJ Community Edition
}

tasks {
    // Set compatibility levels
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("232")
        untilBuild.set("242.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
