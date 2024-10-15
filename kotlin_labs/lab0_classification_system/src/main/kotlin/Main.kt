package main

import java.io.File
import java.util.Scanner
import kotlin.collections.mutableListOf
import kotlin.collections.single
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Greeter() {
  fun greet(greeting: String, name: String, planet: Planet?) {
    println(greeting + ", " + name + "!")
    println("are you from ${planet?.displayName}?")
  }
}

enum class Planet(displayName: String) {
  Earth("Earth"),
  Asgard("Asgard"),
  Betelgeuse("Betelgeuse"),
  Vogsphere("Vogsphere"),
  Kashyyyk("Kashyyyk"),
  Endor("Endor");
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

@Serializable
class JsonCreature(
        val id: Int,
        val isHumanoid: Boolean? = null,
        val planet: Planet? = null,
        val age: Int? = null,
        val traits: Set<Trait>? = null,
) {
  var race: Race? = null

  fun print() {
    println(
            buildString {
              appendLine("JsonCreature {")
              appendLine("  id: $id,")
              if (isHumanoid != null) appendLine("  isHumanoid: $isHumanoid,")
              if (planet != null) appendLine("  planet: $planet,")
              if (age != null) appendLine("  age: $age,")
              if (!traits.isNullOrEmpty()) appendLine("  traits: $traits")
              append("}")
            }
    )
  }

  fun calcRace(): List<Race>? {
    var remainingRaces = enumValues<Race>().toSet()

    if (traits != null) {
      val traitsLocked = traits.toSet()
      traitsLocked.forEach { trait ->
        // if (TraitsIndex.containsKey(trait))
        remainingRaces = remainingRaces.intersect(TraitsIndex[trait]!!)
      }
    }
    // if (PlanetIndex.containsKey(planet))
    if (planet != null) remainingRaces = remainingRaces.intersect(PlanetIndex[planet]!!)

    if (age != null)
            remainingRaces =
                    remainingRaces
                            .filter { Creatures[it]?.age == null || age <= Creatures[it]!!.age }
                            .toSet()

    if (isHumanoid != null)
            remainingRaces =
                    remainingRaces
                            .filter {
                              Creatures[it]?.isHumanoid == null ||
                                      Creatures[it]!!.isHumanoid == isHumanoid
                            }
                            .toSet()

    if (remainingRaces.size != 1) {
      return remainingRaces.sorted()
    }

    race = remainingRaces.sorted().single()
    return null
  }
}

class Universe(val name: String) {
  var creatues = mutableListOf<JsonCreature>()
  fun save() {
    File("src/main/resources/output/${name}.json").writeText(Json.encodeToString(creatues))
  }
  fun append(creature: JsonCreature) {
    creatues.add(creature)
  }
}

enum class Multiverse()

val LotrUniverse = Universe("lotr")
val StarWarsUniverse = Universe("starWars")
val HitchHikerUniverse = Universe("hitchHiker")
val MarvelUniverse = Universe("marvel")

class Creature(
        val race: Race,
        val isHumanoid: Boolean,
        val planet: Planet,
        val age: Int,
        val traits: Set<Trait>,
        val universe: Universe
) {}

val Creatures: Map<Race, Creature> =
        mapOf(
                Race.WOOKIE to
                        Creature(
                                universe = StarWarsUniverse,
                                race = Race.WOOKIE,
                                isHumanoid = false,
                                planet = Planet.Kashyyyk,
                                age = 400,
                                traits = setOf(Trait.HAIRY, Trait.TALL)
                        ),
                Race.EWOK to
                        Creature(
                                universe = StarWarsUniverse,
                                race = Race.EWOK,
                                isHumanoid = false,
                                planet = Planet.Endor,
                                age = 60,
                                traits = setOf(Trait.SHORT, Trait.HAIRY)
                        ),
                Race.ASGARDIAN to
                        Creature(
                                universe = MarvelUniverse,
                                race = Race.ASGARDIAN,
                                isHumanoid = true,
                                planet = Planet.Asgard,
                                age = 5000,
                                traits = setOf(Trait.BLONDE, Trait.TALL)
                        ),
                Race.BETELGEUSIAN to
                        Creature(
                                universe = HitchHikerUniverse,
                                race = Race.BETELGEUSIAN,
                                isHumanoid = true,
                                planet = Planet.Betelgeuse,
                                age = 100,
                                traits = setOf(Trait.EXTRA_ARMS, Trait.EXTRA_HEAD)
                        ),
                Race.VOGONS to
                        Creature(
                                universe = HitchHikerUniverse,
                                race = Race.VOGONS,
                                isHumanoid = false,
                                planet = Planet.Vogsphere,
                                age = 200,
                                traits = setOf(Trait.GREEN, Trait.BULKY)
                        ),
                Race.ELF to
                        Creature(
                                universe = LotrUniverse,
                                race = Race.ELF,
                                isHumanoid = true,
                                planet = Planet.Earth,
                                age = Int.MAX_VALUE,
                                traits = setOf(Trait.BLONDE, Trait.POINTY_EARS, Trait.TALL)
                        ),
                Race.DWARF to
                        Creature(
                                universe = LotrUniverse,
                                race = Race.DWARF,
                                isHumanoid = true,
                                planet = Planet.Earth,
                                age = 200,
                                traits = setOf(Trait.SHORT, Trait.BULKY)
                        )
        )

fun Map<Race, Creature>.indexTraits(): Map<Trait, Set<Race>> {
  val resultMap = mutableMapOf<Trait, MutableSet<Race>>()
  for ((key, creature) in this) {
    for (trait in creature.traits) {
      if (resultMap[trait] == null) {
        resultMap[trait] = mutableSetOf()
      }
      resultMap[trait]!!.add(key)
    }
    // creature.traits.forEach { trait ->
    //  if (resultMap[trait] == null) {
    //    resultMap[trait] = mutableSetOf()
    //  }
    //  resultMap[trait]!!.add(key)
    // }
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

@Serializable class InputData(val data: List<JsonCreature>)

fun main() {
  val inputFile = File("src/main/resources/input.json")
  val creatures = Json.decodeFromString<InputData>(inputFile.readText()).data
  for (creature in creatures) {
    val options = creature.calcRace()
    if (options == null) {
      println("Universe: ${Creatures[creature.race]!!.universe.name}")
      println("Race: ${creature.race}")
      creature.print()
      println("-------")
      Creatures[creature.race]!!.universe.append(creature)
      continue
    }
    if (options.size == 0) {
      println("(Unknown race")
      creature.print()
      println("-------")
      continue
    }
    while (creature.race == null) {
      println("(id: ${creature.id})")
      println("More than one possible classifications for creature:")
      creature.print()
      println("Choose manually:")
      var i = 1
      options.forEach { option ->
        println("${i}. ${option.displayName}")
        i += 1
      }
      val input = Scanner(System.`in`).nextLine()
      val num = input?.toInt() ?: 0
      if (num <= 0 || num > options.size) {
        println("invalid option selected, try again")
      } else {
        creature.race = options[num - 1]
      }
    }
    println("Universe: ${Creatures[creature.race]!!.universe.name}")
    println("Race: ${creature.race}")
    creature.print()
    println("------")
    Creatures[creature.race]!!.universe.append(creature)
  }
  LotrUniverse.save()
  MarvelUniverse.save()
  HitchHikerUniverse.save()
  StarWarsUniverse.save()
}
