package lab3

import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

fun main() = runBlocking {
  Scheduler.processCarFiles()

  Statistics.printStats()
}
