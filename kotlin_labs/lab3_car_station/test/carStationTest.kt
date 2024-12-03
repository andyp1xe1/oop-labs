package lab3

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CarStationSpec :
        StringSpec({
          "should throw ImpossibleToServeException when attempting to serve wrong passenger type" {
            val carStation1 = CarStation(RobotDinner(), GasStation(), ArrayQueue())
            val carStation2 = CarStation(PeopleDinner(), ElectricStation(), ArrayQueue())

            val peopleCar =
                    Car(1, CarType.GAS, PassengerType.PEOPLE, isDining = true, consumption = 50)
            val robotCar =
                    Car(
                            2,
                            CarType.ELECTRIC,
                            PassengerType.ROBOTS,
                            isDining = true,
                            consumption = 30
                    )

            shouldThrow<ImpossibleToServeException> { carStation1.addCar(peopleCar) }
            shouldThrow<ImpossibleToServeException> { carStation2.addCar(robotCar) }
          }

          "should serve only cars marked for dining" {
            Statistics.reset()
            val dinnerService = PeopleDinner()
            val refuelingService = GasStation()

            val carStation = CarStation(dinnerService, refuelingService, ArrayQueue())

            val diningCar1 =
                    Car(1, CarType.GAS, PassengerType.PEOPLE, isDining = true, consumption = 30)
            val diningCar2 =
                    Car(2, CarType.GAS, PassengerType.PEOPLE, isDining = true, consumption = 40)
            val nonDiningCar =
                    Car(3, CarType.GAS, PassengerType.PEOPLE, isDining = false, consumption = 50)

            carStation.addCar(diningCar1)
            carStation.addCar(nonDiningCar)
            carStation.addCar(diningCar2)

            carStation.serveCars()
            Statistics.getDinedCount() shouldBe 2
          }
        })
