plugins {
    id("java")
    id("io.freefair.lombok") version "8.4"
    jacoco
}

group = "xyz.ocooleast.auth"
version = "0.0.1"

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

tasks.test {
    useJUnitPlatform()
}
