package lab3

import java.io.File
import java.nio.file.*
import kotlin.io.println
import kotlinx.coroutines.*

object Scheduler {
  private val carFilesPath = Paths.get("./queue/")

  suspend fun processCarFiles() = coroutineScope {
    launch { watchForCarFiles() }
    launch { serveCarStations() }
  }

  private suspend fun watchForCarFiles() = coroutineScope {
    // val outputLog = File("./logs/generator_output.log")
    // outputLog.parentFile.mkdirs()

    // val generator =
    //        ProcessBuilder("python3", "./scripts/generator.py").redirectOutput(outputLog).start()

    println("Scheduler: Using watcher for directory: $carFilesPath")

    // val generatorCompleted = CompletableDeferred<Unit>()
    // launch {
    //  generator.waitFor()
    //  generator.destroy()
    //  generatorCompleted.complete(Unit)
    // }

    while (isActive) {
      Files.list(carFilesPath).use { files ->
        files.forEach { file ->
          println("Scheduler: New file detected: $file")
          launch { handleCarFile(file.toFile()) }
        }
      }

      // if (generatorCompleted.isCompleted) {
      //  println("Scheduler: Generator stopped and no more files to process. Exiting watcher.")
      //  break
      // }

      println("yielding from watcher")
      delay(2000)
      // yield()
    }
  }

  private suspend fun handleCarFile(file: File) {
    println("Scheduler: Handling file: ${file.name}")
    val carData = withContext(Dispatchers.IO) { file.readText() }
    val car = carFromJSON(carData)
    Semaphore.guideCar(car)
    println("Scheduler: Car added: ${car}")
    file.delete()
  }

  private suspend fun serveCarStations() = coroutineScope {
    while (isActive) {
      launch { Semaphore.CarStationRE.serveCars() }
      launch { Semaphore.CarStationRG.serveCars() }
      launch { Semaphore.CarStationHE.serveCars() }
      launch { Semaphore.CarStationHG.serveCars() }

      // println("yielding from server")
      delay(3000)
      // yield()
    }
  }
}
