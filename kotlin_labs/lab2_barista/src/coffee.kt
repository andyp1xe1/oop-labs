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

open class Coffee(open val intensity: Intensity = Intensity.NORMAL) {
  open val name = "Coffee"
  open fun printCoffeeDetails() {
    println("Coffee intensity: $intensity")
  }
}

class Americano(
        intensity: Intensity,
        val mlOfWater: Int = 60,
) : Coffee(intensity) {
  override val name = "Americano"
  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("Americano water: $mlOfWater ml")
  }
}

open class Cappuccino(
        intensity: Intensity = Intensity.NORMAL,
        open val mlOfMilk: Int = 60,
) : Coffee(intensity) {
  override val name = "Cappuccino"
  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("Cappuccino milk: $mlOfMilk ml")
  }
}

class SyrupCappuccino(
        intensity: Intensity = Intensity.NORMAL,
        mlOfMilk: Int = 60,
        val mlOfSyrup: Int,
) : Cappuccino(intensity, mlOfMilk) {
  override val name = "SyrupCappuccino"
  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("SyrupCappuccino syrup: $mlOfSyrup ml")
  }
}

class PumpkinSpiceLatte(
        intensity: Intensity = Intensity.NORMAL,
        val mlOfMilk: Int = 60,
) : Coffee(intensity) {
  override val name = "PumpkinSpiceLatte"
  override fun printCoffeeDetails() {
    super.printCoffeeDetails()
    println("Pumpkin Spice Latte milk: $mlOfMilk ml")
  }
}
