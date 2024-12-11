package lab3

interface Dineable {
  fun serveDinner(carID: Int)
}

interface Refuelable {
  fun refuel(carID: Int, passengerType: PassengerType, consumption: Int)
}

class PeopleDinner : Dineable {
  override fun serveDinner(carID: Int) {
    println("Serving dinner to people in car ${carID}.")
    Statistics.registerDined(this)
  }
}

class RobotDinner : Dineable {
  override fun serveDinner(carID: Int) {
    println("Serving dinner to robots in car ${carID}.")
    Statistics.registerDined(this)
  }
}

class ElectricStation : Refuelable {
  override fun refuel(carID: Int, passengerType: PassengerType, consumption: Int) {
    println("Refueling electric car ${carID}.")
    Statistics.registerRefuled(this, consumption)
    Statistics.registerPassengerType(passengerType)
  }
}

class GasStation : Refuelable {
  override fun refuel(carID: Int, passengerType: PassengerType, consumption: Int) {
    println("Refueling gas car ${carID}.")
    Statistics.registerRefuled(this, consumption)
    Statistics.registerPassengerType(passengerType)
  }
}

object Statistics {
  var electricCount = 0
    private set
  var gasCount = 0
    private set
  var electricConsumption = 0
    private set
  var gasConsumption = 0
    private set
  var robotsDinedCount = 0
    private set
  var peopleDinedCount = 0
    private set
  var peopleCount = 0
    private set
  var robotsCount = 0
    private set

  fun registerDined(obj: Dineable) {
    when (obj) {
      is RobotDinner -> robotsDinedCount++
      is PeopleDinner -> peopleDinedCount++
    }
  }

  fun registerPassengerType(type: PassengerType) {
    when {
      type == PassengerType.PEOPLE -> peopleCount++
      else -> robotsCount++
    }
  }

  fun registerRefuled(obj: Refuelable, carConsumption: Int) {
    when (obj) {
      is ElectricStation -> {
        electricCount++
        electricConsumption += carConsumption
      }
      is GasStation -> {
        gasCount++
        gasConsumption += carConsumption
      }
    }
  }

  fun getDinedCount() = robotsDinedCount + peopleDinedCount
  fun getNotDinedCount() = robotsCount + peopleCount - getDinedCount()

  fun printStats() {
    println("ELECTRIC: ${electricCount}")
    println("GAS: ${gasCount}")
    println("PEOPLE: ${peopleCount}")
    println("ROBOTS: ${robotsCount}")
    println("DINING: ${getDinedCount()}")
    println("NOT_DINING: ${getNotDinedCount()}")
    println("ELECTRIC_CONSUMPTION: ${electricConsumption}")
    println("GAS_CONSUMPTION: ${gasConsumption}")
  }

  fun reset() {
    electricCount = 0
    gasCount = 0
    electricConsumption = 0
    gasConsumption = 0
    robotsDinedCount = 0
    peopleDinedCount = 0
    robotsCount = 0
    peopleCount = 0
  }
}
