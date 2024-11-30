plugins {
  kotlin("jvm")
  application
}

repositories {
  mavenCentral()
}

dependencies {
  // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5-jvm
  testImplementation("io.kotest:kotest-runner-junit5-jvm:6.0.0.M1")
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

sourceSets.getByName("main").kotlin.srcDir("src/")
sourceSets.getByName("test").kotlin.srcDir("test/")

application { mainClass = "lab3.MainKt" }

tasks.withType<Test>().configureEach {
   useJUnitPlatform()
}
