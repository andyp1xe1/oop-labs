package coffee

enum class Intensity() {
  LIGHT,
  NORMAL,
  STRONG,
}

enum class Syrup() {
  MACADAMIA,
  VANILLA,
  COCONUT,
  CARAMEL,
  CHOCOLATE,
  POPCORN,
}

open class Coffee(private val intensity: Intensity = Intensity.NORMAL) {
  protected open val name = "Coffee"

  open fun printCoffeeDetails() {
    println("Coffee intensity: $intensity")
  }

  protected open fun brew() {
    println("Making $name")
    println("Intensity set to $intensity")
  }

  fun makeCoffee(): Coffee {
    brew()
    return this
  }
}

internal class Americano(
        private val mlOfWater: Int = 60,
        intensity: Intensity = Intensity.NORMAL,
) : Coffee(intensity) {
  override val name = "Americano"

  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("$name water: $mlOfWater ml")
  }

  override fun brew() {
    super.brew()
    println("Adding $mlOfWater of water")
  }

  fun makeAmericano(): Americano {
    brew()
    return this
  }
}

open class Cappuccino(
        private val mlOfMilk: Int = 60,
        intensity: Intensity = Intensity.NORMAL,
) : Coffee(intensity) {
  override val name = "Cappuccino"

  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("$name milk: $mlOfMilk ml")
  }

  override fun brew() {
    super.brew()
    println("Adding $mlOfMilk of milk")
  }

  fun makeCappuccino(): Cappuccino {
    brew()
    return this
  }
}

class SyrupCappuccino(
        private val syrupType: Syrup,
        private val mlOfSyrup: Int = 10,
        intensity: Intensity = Intensity.NORMAL,
        mlOfMilk: Int = 60,
) : Cappuccino(mlOfMilk, intensity) {
  override val name = "Syrup Cappuccino"

  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("$name syrup: $mlOfSyrup ml")
  }

  override fun brew() {
    super.brew()
    println("Adding $mlOfSyrup of $syrupType syrup")
  }

  fun makeSyrupCappuccino(): SyrupCappuccino {
    brew()
    return this
  }
}

class PumpkinSpiceLatte(
        val mlOfMilk: Int = 100,
        val mlOfPumpkinSpice: Int = 15,
        intensity: Intensity = Intensity.NORMAL,
) : Coffee(intensity) {
  override val name = "PumpkinSpiceLatte"

  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("$name milk: $mlOfMilk ml")
  }

  override fun brew() {
    super.brew()
    println("Steaming $mlOfMilk ml of milk")
    println("Adding $mlOfPumpkinSpice ml of pumpkin spice to the milk")
    println("Pouring the milk in coffee")
  }

  fun makePumpkinSpiceLatte(): PumpkinSpiceLatte {
    brew()
    return this
  }
}
