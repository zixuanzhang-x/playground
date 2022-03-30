plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.zixuan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("io.projectreactor:reactor-bom:2020.0.15"))
    implementation ("io.projectreactor:reactor-core")
    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("io.projectreactor:reactor-test")
}