package lab3

object Semaphore {
  val CarStationRE = CarStation(RobotDinner(), ElectricStation(), CircularQueue(30))
  val CarStationRG = CarStation(RobotDinner(), GasStation(), CircularQueue(30))
  val CarStationHE = CarStation(PeopleDinner(), ElectricStation(), CircularQueue(30))
  val CarStationHG = CarStation(PeopleDinner(), GasStation(), CircularQueue(30))

  fun guideCar(car: Car) {
    when {
      car.type == CarType.ELECTRIC && car.passengers == PassengerType.ROBOTS ->
              CarStationRE.addCar(car)
      car.type == CarType.GAS && car.passengers == PassengerType.ROBOTS -> {
        CarStationRG.addCar(car)
      }
      car.type == CarType.ELECTRIC && car.passengers == PassengerType.PEOPLE -> {
        CarStationHE.addCar(car)
      }
      car.type == CarType.GAS && car.passengers == PassengerType.PEOPLE -> {
        CarStationHG.addCar(car)
      }
    }
  }
}
