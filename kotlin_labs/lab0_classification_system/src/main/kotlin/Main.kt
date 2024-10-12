package main

class Greeter() {
  fun greet(greeting: String, name: String, planet: Planet?) {
    println(greeting + ", " + name + "!")
    println("are you from ${planet?.displayName}?")
  }
}

enum class Planet(displayName: String) {
  EARTH("Earth"),
  ASGARD("Asgard"),
  BETELGEUSE("Betelgeuse"),
  VOGSPHERE("Vogsphere"),
  KASHYYYK("Kashyyyk"),
  ENDOR("Endor");
  val displayName = displayName
}

fun String.toPlanet(): Planet? {
  return Planet.values().find { it.name.equals(this, ignoreCase = true) }
}

fun main() {
  val planet = "Earth".toPlanet()
  Greeter().greet("Hello there", "Obiwan", planet)
}
