plugins {
    application
    idea
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    flatDir {
        dirs("lib")
    }
}

dependencies {
    implementation(files("lib/mazing.jar"))

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

application {
    mainClass = "fr.ubordeaux.ao.project.Main"
    //mainClass = "fr.ubordeaux.ao.project.Test.TestGame"
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

//a supprimer pour le rendu, sert juste pour le debug de modele dans le main
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}