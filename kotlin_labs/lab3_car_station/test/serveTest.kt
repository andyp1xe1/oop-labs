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

            gasStation1.refuel("car1", PassengerType.ROBOTS, 24)
            gasStation2.refuel("car2", PassengerType.ROBOTS, 43)
            gasStation2.refuel("car3", PassengerType.ROBOTS, 25)
            electricStation1.refuel("car4", PassengerType.PEOPLE, 50)
            electricStation1.refuel("car5", PassengerType.PEOPLE, 25)
            electricStation2.refuel("car6", PassengerType.PEOPLE, 100)
            electricStation2.refuel("car7", PassengerType.PEOPLE, 25)

            robotDinner1.serveDinner("car1")
            robotDinner2.serveDinner("car2")
            robotDinner2.serveDinner("car3")
            peopleDinner1.serveDinner("car4")
            peopleDinner2.serveDinner("car5")

            // fuel tracking testing
            Statistics.gasCount shouldBe 3
            Statistics.electricCount shouldBe 4

            // passenger tracking testing
            Statistics.peopleCount shouldBe 4
            Statistics.robotsCount shouldBe 3

            // consumption tracking testing
            Statistics.gasConsumption shouldBe 92
            Statistics.electricConsumption shouldBe 200

            // dinable class testing
            Statistics.peopleDinedCount shouldBe 2
            Statistics.robotsDinedCount shouldBe 3
          }
        })
