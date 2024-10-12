package main

import kotlin.io.println

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

enum class Trait(displayName: String) {
  HAIRY("HAIRY"),
  TALL("TAIL"),
  SHORT("SHORT"),
  BLONDE("BLONDE"),
  EXTRA_ARMS("EXTRA_ARMS"),
  EXTRA_HEAD("EXTRA_HEAD"),
  GREEN("GREEN"),
  BULKY("BULKY"),
  POINTY_EARS("POINTY_EARS");
  val displayName = displayName
}

enum class Race(displayName: String) {
  ELF("Elf"),
  WOOKIE("Wookie"),
  EWOK("Ewok"),
  ASGARDIAN("Asgardian"),
  BETELGEUSIAN("Betelgeusian"),
  VOGONS("Vogons"),
  DWARF("Dwarf");
  val displayName = displayName
}

class Creature(
        val isHumanoid: Boolean?,
        val planet: Planet?,
        val age: Int?,
        val traits: List<Trait>?
) {
  var race: Race? = null

  fun calcRace() {}
}

fun main() {
  val elf =
          Creature(
                  isHumanoid = true,
                  planet = Planet.EARTH,
                  age = 2000,
                  traits = listOf(Trait.SHORT)
          )
  println("isHumanoid: ${elf.isHumanoid}")
  println("planet: ${elf.planet}")
  println("age: ${elf.age}")
  elf.traits?.forEach { trait -> println("Trait: ${trait.name}") }
}
