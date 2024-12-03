package lab3

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class SemaphoreTest :
        StringSpec({
          "Semaphore should guide cars correctly and register dining stats accuretly from JSON" {
            Statistics.reset()

            val jsonInput =
                    """
[
  {"id": 1, "type": "GAS", "passengers": "ROBOTS", "isDining": true, "consumption": 15},
  {"id": 2, "type": "GAS", "passengers": "ROBOTS", "isDining": false, "consumption": 24},
  {"id": 3, "type": "GAS", "passengers": "ROBOTS", "isDining": false, "consumption": 11},
  {"id": 4, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": true, "consumption": 44},
  {"id": 5, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": true, "consumption": 42},
  {"id": 6, "type": "GAS", "passengers": "PEOPLE", "isDining": true, "consumption": 33},
  {"id": 7, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": true, "consumption": 33},
  {"id": 8, "type": "ELECTRIC", "passengers": "ROBOTS", "isDining": false, "consumption": 23},
  {"id": 9, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": true, "consumption": 37},
  {"id": 10, "type": "ELECTRIC", "passengers": "ROBOTS", "isDining": true, "consumption": 35},
  {"id": 11, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": false, "consumption": 29},
  {"id": 12, "type": "GAS", "passengers": "PEOPLE", "isDining": false, "consumption": 38},
  {"id": 13, "type": "ELECTRIC", "passengers": "PEOPLE", "isDining": false, "consumption": 46},
  {"id": 14, "type": "GAS", "passengers": "ROBOTS", "isDining": true, "consumption": 27},
  {"id": 15, "type": "ELECTRIC", "passengers": "ROBOTS", "isDining": true, "consumption": 49},
  {"id": 16, "type": "GAS", "passengers": "PEOPLE", "isDining": true, "consumption": 27}
]"""

            val cars: List<Car> = Json.decodeFromString(jsonInput)

            shouldNotThrow<ImpossibleToServeException> {
              cars.forEach { car -> Semaphore.guideCar(car) }
            }

            Semaphore.CarStationRE.serveCars()
            Semaphore.CarStationRG.serveCars()
            Semaphore.CarStationHE.serveCars()
            Semaphore.CarStationHG.serveCars()

            Statistics.electricCount shouldBe 9
            Statistics.gasCount shouldBe 7

            Statistics.peopleDinedCount shouldBe 6
            Statistics.robotsDinedCount shouldBe 4

            Statistics.printStats()
          }
        })
