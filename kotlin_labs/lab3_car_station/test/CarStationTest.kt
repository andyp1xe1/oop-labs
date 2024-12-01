package lab3

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CarStationSpec :
        StringSpec({
          "should throw ImpossibleToServeException when attempting to serve wrong passenger type" {
            val carStation1 = CarStation(RobotDinner(), GasStation(), ArrayQueue())
            val carStation2 = CarStation(PeopleDinner(), ElectricStation(), ArrayQueue())

            val peopleCar = Car("car1", CarType.GAS, PassengerType.PEOPLE, isDining = true)
            val robotCar = Car("car2", CarType.ELECTRIC, PassengerType.ROBOTS, isDining = true)

            shouldThrowAny { carStation1.addCar(peopleCar) }
            shouldThrowAny { carStation2.addCar(robotCar) }
          }

          "should serve only cars marked for dining" {
            Statistics.reset()
            val dinnerService = PeopleDinner()
            val refuelingService = GasStation()

            val carStation = CarStation(dinnerService, refuelingService, ArrayQueue())

            val diningCar1 = Car("car1", CarType.GAS, PassengerType.PEOPLE, isDining = true)
            val diningCar2 = Car("car2", CarType.GAS, PassengerType.PEOPLE, isDining = true)
            val nonDiningCar = Car("car3", CarType.GAS, PassengerType.PEOPLE, isDining = false)

            carStation.addCar(diningCar1)
            carStation.addCar(nonDiningCar)
            carStation.addCar(diningCar2)

            carStation.serveCars()
            Statistics.getPeopleCount() shouldBe 2
          }
        })
