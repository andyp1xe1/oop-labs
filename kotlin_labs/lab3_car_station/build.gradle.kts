plugins {
  kotlin("jvm")
  application
  kotlin("plugin.serialization") version "2.0.20"
}

repositories { mavenCentral() }

dependencies {
  // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5-jvm
  testImplementation("io.kotest:kotest-runner-junit5-jvm:6.0.0.M1")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

val run by tasks.getting(JavaExec::class) { standardInput = System.`in` }

sourceSets.getByName("main").kotlin.srcDir("src/")

sourceSets.getByName("test").kotlin.srcDir("test/")

application { mainClass = "lab3.MainKt" }

tasks.withType<Test>().configureEach { useJUnitPlatform() }

tasks.test {
    // Show standard output and error in console
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
