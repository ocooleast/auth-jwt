import java.lang.System.getenv

plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    jacoco
    `maven-publish`
}

group = "xyz.ocooleast.auth"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jjwk.api)
    implementation(libs.jjwk.jackson)
    runtimeOnly(libs.jjwk.impl)
    compileOnly(libs.lombok)
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ocooleast/auth-jwt")
            credentials {
                username = "ocooleast"
                password = getenv("access_token")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}


tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports {
        csv.required = true
        html.required = false
    }
}
