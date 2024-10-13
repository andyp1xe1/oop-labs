package main

import kotlin.collections.single
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

class JsonCreature(
        val id: Int,
        val planet: Planet?,
        val age: Int?,
        val traits: Set<Trait>?,
)

class Creature(val isHumanoid: Boolean, val planet: Planet, val age: Int, val traits: Set<Trait>) {
  var race: Race? = null

  fun calcRace() {
    var remainingRaces = enumValues<Race>().toSet()

    traits.forEach { trait ->
      // if (TraitsIndex.containsKey(trait))
      remainingRaces = remainingRaces.intersect(TraitsIndex[trait]!!)
    }
    // if (PlanetIndex.containsKey(planet))
    remainingRaces = remainingRaces.intersect(PlanetIndex[planet]!!)

    remainingRaces =
            remainingRaces
                    .filter { Creatures[it]?.age == null || age <= Creatures[it]!!.age }
                    .toSet()
    remainingRaces =
            remainingRaces
                    .filter {
                      Creatures[it]?.isHumanoid == null || Creatures[it]!!.isHumanoid == isHumanoid
                    }
                    .toSet()

    if (remainingRaces.size != 1) {
      return
    }

    race = remainingRaces.sorted().single()
  }
}

val Creatures: Map<Race, Creature> =
        mapOf(
                Race.WOOKIE to
                        Creature(
                                isHumanoid = false,
                                planet = Planet.KASHYYYK,
                                age = 400,
                                traits = setOf(Trait.HAIRY, Trait.TALL)
                        ),
                Race.EWOK to
                        Creature(
                                isHumanoid = false,
                                planet = Planet.ENDOR,
                                age = 60,
                                traits = setOf(Trait.SHORT, Trait.HAIRY)
                        ),
                Race.ASGARDIAN to
                        Creature(
                                isHumanoid = true,
                                planet = Planet.ASGARD,
                                age = 5000,
                                traits = setOf(Trait.BLONDE, Trait.TALL)
                        ),
                Race.BETELGEUSIAN to
                        Creature(
                                isHumanoid = true,
                                planet = Planet.BETELGEUSE,
                                age = 100,
                                traits = setOf(Trait.EXTRA_ARMS, Trait.EXTRA_HEAD)
                        ),
                Race.VOGONS to
                        Creature(
                                isHumanoid = false,
                                planet = Planet.VOGSPHERE,
                                age = 200,
                                traits = setOf(Trait.GREEN, Trait.BULKY)
                        ),
                Race.ELF to
                        Creature(
                                isHumanoid = true,
                                planet = Planet.EARTH,
                                age = Int.MAX_VALUE,
                                traits = setOf(Trait.BLONDE, Trait.POINTY_EARS)
                        ),
                Race.DWARF to
                        Creature(
                                isHumanoid = true,
                                planet = Planet.EARTH,
                                age = 200,
                                traits = setOf(Trait.SHORT, Trait.BULKY)
                        )
        )

fun Map<Race, Creature>.indexTraits(): Map<Trait, Set<Race>> {
  val resultMap = mutableMapOf<Trait, MutableSet<Race>>()
  for ((key, creature) in this) {
    creature.traits.forEach { trait ->
      if (resultMap[trait] == null) {
        resultMap[trait] = mutableSetOf()
      }
      resultMap[trait]!!.add(key)
    }
  }
  return resultMap.mapKeys { it.key }.mapValues { it.value.toSet() }
}

val TraitsIndex = Creatures.indexTraits()

fun Map<Race, Creature>.indexPlanet(): Map<Planet, Set<Race>> {
  val resultMap = mutableMapOf<Planet, MutableSet<Race>>()
  for ((key, creature) in this) {
    val planet = creature.planet
    if (resultMap[planet] == null) {
      resultMap[planet] = mutableSetOf()
    }
    resultMap[planet]!!.add(key)
  }
  return resultMap
}

val PlanetIndex = Creatures.indexPlanet()

fun main() {
  val elf =
          Creature(
                  isHumanoid = true,
                  planet = Planet.EARTH,
                  age = 2000,
                  traits = setOf(Trait.BLONDE)
          )
  println("isHumanoid: ${elf.isHumanoid}")
  println("planet: ${elf.planet}")
  println("age: ${elf.age}")
  elf.traits.forEach { trait -> println("Trait: ${trait.name}") }
  elf.calcRace()

  println("Race of elf creature found to be: ${elf.race}")
}
