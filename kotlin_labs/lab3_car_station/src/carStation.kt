package lab3

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class ImpossibleToServeException(message: String) : Exception(message)

@Serializable
data class Car(
        val id: Int,
        val type: CarType,
        val passengers: PassengerType,
        val isDining: Boolean,
        val consumption: Int
)

fun carFromJSON(str: String): Car {
  return Json.decodeFromString(str)
}

@Serializable
enum class CarType(displayName: String) {
  ELECTRIC("ELECTRIC"),
  GAS("GAS");
  val displayName = displayName
}

@Serializable
enum class PassengerType(displayName: String) {
  PEOPLE("PEOPLE"),
  ROBOTS("ROBOTS");
  val displayName = displayName
}

class CarStation(
        private val diningService: Dineable,
        private val refuelingService: Refuelable,
        private val queue: Queue<Car>
) {

  fun queueSize(): Int {
    return queue.size()
  }

  fun addCar(car: Car) {
    when {
      diningService is RobotDinner && car.passengers == PassengerType.PEOPLE ->
              throw ImpossibleToServeException("Passenger is not human")
      diningService is PeopleDinner && car.passengers == PassengerType.ROBOTS ->
              throw ImpossibleToServeException("Passenger is not robot")
      refuelingService is GasStation && car.type == CarType.ELECTRIC ->
              throw ImpossibleToServeException("Car is not gas based")
      refuelingService is ElectricStation && car.type == CarType.GAS ->
              throw ImpossibleToServeException("Car is not electric")
    }
    queue.enqueue(car)
  }

  fun serveCars() {
    while (!queue.isEmpty()) {
      val car = queue.dequeue() ?: break

      if (car.isDining) {
        diningService.serveDinner(car.id)
      }

      refuelingService.refuel(car.id, car.passengers, car.consumption)
    }
  }
}
