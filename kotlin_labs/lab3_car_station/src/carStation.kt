package lab3

class ImpossibleToServeException(message: String) : Exception(message)

data class Car(
        val id: String,
        val type: CarType,
        val passengerType: PassengerType,
        val isDining: Boolean
)

enum class CarType {
  ELECTRIC,
  GAS
}

enum class PassengerType {
  PEOPLE,
  ROBOTS
}

class CarStation(
        private val diningService: Dineable,
        private val refuelingService: Refuelable,
        private val queue: Queue<Car>
) {

  fun addCar(car: Car) {
    when {
      diningService is RobotDinner && car.passengerType == PassengerType.PEOPLE ->
              throw ImpossibleToServeException("Passenger is not human")
      diningService is PeopleDinner && car.passengerType == PassengerType.ROBOTS ->
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

      refuelingService.refuel(car.id)
    }
  }
}
