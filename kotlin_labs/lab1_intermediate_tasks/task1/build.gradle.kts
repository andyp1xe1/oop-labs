plugins {
  kotlin("jvm") version "2.0.20"
  application
}

repositories { mavenCentral() }

dependencies {
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

application { mainClass = "main.MainKt" }
