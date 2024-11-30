package lab2

import barista.*
import coffee.*

fun main() {
  println("Welcome to the Coffee Shop!")
  while (true) {
    getOrder()
    println("Starting to brew your orders...")
    Barista.brew()
  }
}

fun promptCoffeeType(): CoffeeType? {
  println("Choose your coffee type (or type 'done' to finish ordering):")
  val choice = readLine()?.trim()
  if (choice == "done") return null

  val index = choice?.toIntOrNull()?.minus(1)
  return CoffeeType.values().getOrNull(index ?: -1).also {
    if (it == null) println("Invalid choice. Please try again.")
  }
}

fun getOrder() {
  while (true) {
    Barista.displayMenu()
    val coffeeType = promptCoffeeType() ?: break

    val intensity = promptIntensity()

    val mlOfWater =
            if (coffeeType == CoffeeType.AMERICANO) promptInt("Enter ml of water", default = 60)
            else 60

    val mlOfMilk =
            if (coffeeType == CoffeeType.PUMPKIN_SPICE_LATTE) promptInt("Enter ml of milk", 100)
            else if (coffeeType in
                            listOf(
                                    CoffeeType.CAPPUCCINO,
                                    CoffeeType.SYRUP_CAPPUCCINO,
                                    CoffeeType.PUMPKIN_SPICE_LATTE
                            )
            )
                    promptInt("Enter ml of milk", default = 60)
            else 0

    val syrup = if (coffeeType == CoffeeType.SYRUP_CAPPUCCINO) promptSyrupType() else null

    val mlOfSyrup =
            if (coffeeType == CoffeeType.SYRUP_CAPPUCCINO)
                    promptInt("Enter ml of syrup", default = 15)
            else 0

    val mlOfPumpkinSpice =
            if (coffeeType == CoffeeType.PUMPKIN_SPICE_LATTE)
                    promptInt("Enter ml of pumpkin spice", default = 15)
            else 0

    val order =
            Order(
                    type = coffeeType,
                    intensity = intensity,
                    mlOfWater = mlOfWater,
                    mlOfMilk = mlOfMilk,
                    syrup = syrup,
                    mlOfSyrup = mlOfSyrup,
                    mlOfPumpkinSpice = mlOfPumpkinSpice,
            )

    Barista.addOrder(order)
  }
}

fun promptIntensity(): Intensity {
  println("Choose intensity (default NORMAL) (1 = LIGHT, 2 = NORMAL, 3 = STRONG):")
  val choice = readLine()?.trim()?.toIntOrNull() ?: 2
  return Intensity.values().getOrNull(choice - 1) ?: Intensity.NORMAL
}

fun promptSyrupType(): Syrup {
  println("Choose syrup type (default CARAMEL):")
  Syrup.values().forEachIndexed { index, syrupType -> println("${index + 1}. $syrupType") }
  val choice = readLine()?.toIntOrNull() ?: 1
  return Syrup.values().getOrNull(choice - 1) ?: Syrup.CARAMEL
}

fun promptInt(message: String, default: Int): Int {
  println("$message (default $default):")
  return readLine()?.toIntOrNull() ?: default
}
