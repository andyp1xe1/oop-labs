plugins {
  kotlin("jvm") version "2.0.20"
  application
  kotlin("plugin.serialization") version "2.0.20"
}

//kotlin { jvmToolchain(21) }

repositories { mavenCentral() }

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.18.0")
}

application { mainClass = "main.MainKt" }
