plugins {
    application
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.gradleup.shadow") version "9.0.0-beta13"
    id("io.freefair.lombok") version "8.4"
}

group = "com.demo"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass.set("com.demo.fakeaws.SimpleApplication")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("software.amazon.awssdk:aws-sdk-java:2.31.35"))
    implementation("software.amazon.awssdk:sns:2.31.35")
    implementation("software.amazon.awssdk:sqs:2.31.35")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
