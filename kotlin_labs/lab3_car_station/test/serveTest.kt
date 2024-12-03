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

            gasStation1.refuel(1, PassengerType.ROBOTS, 24)
            gasStation2.refuel(2, PassengerType.ROBOTS, 43)
            gasStation2.refuel(3, PassengerType.ROBOTS, 25)
            electricStation1.refuel(4, PassengerType.PEOPLE, 50)
            electricStation1.refuel(5, PassengerType.PEOPLE, 25)
            electricStation2.refuel(6, PassengerType.PEOPLE, 100)
            electricStation2.refuel(7, PassengerType.PEOPLE, 25)

            robotDinner1.serveDinner(1)
            robotDinner2.serveDinner(2)
            robotDinner2.serveDinner(3)
            peopleDinner1.serveDinner(5)
            peopleDinner2.serveDinner(5)

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
