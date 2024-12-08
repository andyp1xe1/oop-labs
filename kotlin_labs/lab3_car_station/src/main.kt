package lab3

import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

fun main() = runBlocking {
  val job = launch { Scheduler.processCarFiles() }
  delay(60_000)
  job.cancelAndJoin()
  Statistics.printStats()
}
