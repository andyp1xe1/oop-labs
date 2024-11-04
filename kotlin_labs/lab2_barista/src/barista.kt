package barista

import coffee.*

enum class CoffeeType() {
  COFFEE,
  AMERICANO,
  CAPPUCCINO,
  SYRUP_CAPPUCCINO,
  PUMPKIN_SPICE_LATTE,
}

data class Order(
        val type: CoffeeType = CoffeeType.COFFEE,
        val intensity: Intensity = Intensity.NORMAL,
        val mlOfWater: Int = 60,
        val mlOfMilk: Int = 60,
        val syrup: Syrup = Syrup.CARAMEL,
        val mlOfSyrup: Int = 15,
        val mlOfPumpkinSpice: Int = 15,
)

fun Order.toCoffee(): Coffee {
  return when (type) {
    CoffeeType.AMERICANO -> Americano(mlOfWater, intensity)
    CoffeeType.CAPPUCCINO -> Cappuccino(mlOfMilk, intensity)
    CoffeeType.SYRUP_CAPPUCCINO ->
            SyrupCappuccino(
                    syrup,
                    mlOfSyrup,
                    intensity,
                    mlOfMilk,
            )
    else -> Coffee(intensity)
  }
}

object Barista {
  private val orders = mutableListOf<Coffee>()

  fun addOrder(order: Order) {
    val c = order.toCoffee()
    orders.addFirst(c)
  }

  fun brew() {
    val temp = orders.toList()
    orders.clear()
    temp.forEach {
      it.printCoffeeDetails()
      println("------------------------------")
      when (it) {
        is Cappuccino -> it.makeCappuccino()
        is SyrupCappuccino -> it.makeSyrupCappuccino()
        is Americano -> it.makeAmericano()
        is PumpkinSpiceLatte -> it.makePumpkinSpiceLatte()
        else -> it.makeCoffee()
      }
      println()
    }
  }
}
