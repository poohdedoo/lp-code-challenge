plugins {
    id("java")
}

group = "com.littlepay.code.challenge"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Logging related dependencies.
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")

    // CSV parsing and generating related dependencies.
    implementation("org.apache.commons:commons-csv:1.10.0")

    implementation("org.jgrapht:jgrapht-core:1.5.2")
}

tasks.test {
    useJUnitPlatform()
}