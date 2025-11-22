plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.jagrosh"
version = "Snapshot"

repositories {
    maven("https://m2.dv8tion.net/releases")
    maven("https://jcenter.bintray.com")
    maven("https://jitpack.io")
    maven("https://m2.duncte123.dev/releases")
    maven("https://maven.lavalink.dev/releases")
    maven("https://m2.chew.pro/releases")
    mavenCentral()
}

dependencies {
    // Discord
    implementation("net.dv8tion:JDA:6.1.1")
    implementation("pw.chew:jda-chewtils:2.2")

    // Music
    implementation("dev.arbjerg:lavaplayer:2.2.1")
    implementation("dev.lavalink.youtube:common:1.5.2")
    implementation("com.github.jagrosh:JLyrics:master-SNAPSHOT")
    implementation("com.dunctebot:sourcemanagers:1.9.0")

    // Internal
    implementation("ch.qos.logback:logback-classic:1.5.21")
    implementation("com.typesafe:config:1.3.2")
    implementation("org.jsoup:jsoup:1.15.3")

    // Test
    testImplementation("junit:junit:4.13.1")
    testImplementation("org.hamcrest:hamcrest-core:1.3")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}

tasks {
    jar {
        enabled = false // Because we only want the shadow jar
    }

    shadowJar {
        archiveClassifier.set("All")

        // Equivalent to AppendingTransformer for reference.conf
        mergeServiceFiles {
            include("reference.conf")
        }

        manifest {
            attributes(
                "Main-Class" to "com.jagrosh.jmusicbot.JMusicBot",
                "Specification-Title" to project.name,
                "Specification-Version" to project.version,
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor-Id" to project.group.toString()
            )
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
