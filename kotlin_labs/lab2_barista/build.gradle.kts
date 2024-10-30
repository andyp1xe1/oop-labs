plugins {
  kotlin("jvm")
  application
}

repositories { mavenCentral() }

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

sourceSets.getByName("main").kotlin.srcDir("src/")

application { mainClass = "lab2.MainKt" }
