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
}

class Americano(
        intensity: Intensity,
        val mlOfWater: Int = 60,
) : Coffee(intensity) {
  override val name = "Americano"
}

open class Cappuccino(
        intensity: Intensity = Intensity.NORMAL,
        open val mlOfMilk: Int = 60,
) : Coffee(intensity) {
  override val name = "Cappuccino"
}

class SyrupCappuccino(
        intensity: Intensity = Intensity.NORMAL,
        mlOfMilk: Int = 60,
        val mlOfSyrup: Int,
) : Cappuccino(intensity, mlOfMilk) {
  override val name = "SyrupCappuccino"
}

class PumpkinSpiceLatte(
        intensity: Intensity = Intensity.NORMAL,
        val mlOfMilk: Int = 60,
) : Coffee(intensity) {
  override val name = "PumpkinSpiceLatte"
}
