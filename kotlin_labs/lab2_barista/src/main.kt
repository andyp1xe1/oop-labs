package lab2

import barista.*
import coffee.Syrup

fun main() {
  Barista.addOrder(Order(CoffeeType.COFFEE))
  Barista.addOrder(Order(CoffeeType.SYRUP_CAPPUCCINO, syrup = Syrup.CHOCOLATE))
  Barista.brew()
}
