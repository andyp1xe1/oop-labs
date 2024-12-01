package lab3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StatisticsTest :
        StringSpec({
          "different instances of the same station type should register as one" {
            Statistics.reset()

            val gasStation1 = GasStation()
            val gasStation2 = GasStation()
            val electricStation1 = ElectricStation()
            val electricStation2 = ElectricStation()

            val peopleDinner1 = PeopleDinner()
            val peopleDinner2 = PeopleDinner()
            val robotDinner1 = RobotDinner()
            val robotDinner2 = RobotDinner()

            gasStation1.refuel("car1")
            gasStation2.refuel("car2")
            electricStation1.refuel("car3")
            electricStation2.refuel("car4")

            peopleDinner1.serveDinner("car5")
            peopleDinner2.serveDinner("car6")
            robotDinner1.serveDinner("car7")
            robotDinner2.serveDinner("car8")

            Statistics.getGasCarCount() shouldBe 2
            Statistics.getElectricCarCount() shouldBe 2
            Statistics.getPeopleCount() shouldBe 2
            Statistics.getRobotCount() shouldBe 2
          }

          "registering different types should not affect each other" {
            Statistics.reset()

            val gasStation = GasStation()
            val peopleDinner = PeopleDinner()

            gasStation.refuel("car1")
            peopleDinner.serveDinner("car2")

            Statistics.getGasCarCount() shouldBe 1
            Statistics.getPeopleCount() shouldBe 1
            Statistics.getElectricCarCount() shouldBe 0
            Statistics.getRobotCount() shouldBe 0
          }
        })
