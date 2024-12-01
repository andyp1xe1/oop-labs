package lab3

interface Dineable {
  fun serveDinner(carId: String)
}

interface Refuelable {
  fun refuel(carId: String)
}

class PeopleDinner : Dineable {
  override fun serveDinner(carId: String) {
    println("Serving dinner to people in car $carId.")
    Statistics.register(this)
  }
}

class RobotDinner : Dineable {
  override fun serveDinner(carId: String) {
    println("Serving dinner to robots in car $carId.")
    Statistics.register(this)
  }
}

class ElectricStation : Refuelable {
  override fun refuel(carId: String) {
    println("Refueling electric car $carId.")
    Statistics.register(this)
  }
}

class GasStation : Refuelable {
  override fun refuel(carId: String) {
    println("Refueling gas car $carId.")
    Statistics.register(this)
  }
}

object Statistics {
  private var electric: Int = 0
  private var gas: Int = 0
  private var robots: Int = 0
  private var people: Int = 0

  fun <T : Dineable> register(obj: T) {
    when (obj) {
      is RobotDinner -> robots++
      is PeopleDinner -> people++
    }
  }

  fun <T : Refuelable> register(obj: T) {
    when (obj) {
      is ElectricStation -> electric++
      is GasStation -> gas++
    }
  }

  fun getElectricCarCount() = electric
  fun getGasCarCount() = gas
  fun getRobotCount() = robots
  fun getPeopleCount() = people

  fun reset() {
    electric = 0
    gas = 0
    robots = 0
    people = 0
  }
}
