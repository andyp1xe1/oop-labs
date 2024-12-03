package lab3

import java.io.File
import java.nio.file.*
import kotlin.io.println
import kotlinx.coroutines.*

object Scheduler {
  private val carFilesPath = Paths.get("./queue/")

  suspend fun processCarFiles() = coroutineScope {
    val watcherJob = launch { watchForCarFiles() }
    watcherJob.join()

    val stationJob = launch { serveCarStations() }
    stationJob.join()
    println("Scheduler: All processing complete.")
  }

  private suspend fun watchForCarFiles() = coroutineScope {
    val outputLog = File("./logs/generator_output.log")
    outputLog.parentFile.mkdirs()

    val generator =
            ProcessBuilder("python3", "./scripts/generator.py").redirectOutput(outputLog).start()

    println("Scheduler: Using watcher for directory: $carFilesPath")

    val processedFiles = mutableSetOf<Path>()

    val generatorCompleted = CompletableDeferred<Unit>()
    launch {
      generator.waitFor()
      generator.destroy()
      generatorCompleted.complete(Unit)
    }

    try {
      while (isActive) {
        Files.list(carFilesPath).use { files ->
          files.filter { file -> file !in processedFiles }.forEach { file ->
            println("Scheduler: New file detected: $file")
            processedFiles.add(file)
            launch { handleCarFile(file.toFile()) }
          }
        }

        if (generatorCompleted.isCompleted) {
          println("Scheduler: Generator stopped and no more files to process. Exiting watcher.")
          break
        }

        delay(300)
      }
    } catch (e: Exception) {
      println("Error in watcher: ${e.message}")
    } finally {
      generator.destroyForcibly()
      println("Scheduler: Watcher terminated.")
    }
  }

  private suspend fun handleCarFile(file: File) {
    println("Scheduler: Handling file: ${file.name}")
    withContext(Dispatchers.IO) {
      val carData = file.readText()
      val car = carFromJSON(carData)
      Semaphore.guideCar(car)
      println("Car added: ${car}")
      file.delete()
    }
  }

  private suspend fun serveCarStations() = coroutineScope {
    try {
      launch { Semaphore.CarStationRE.serveCars() }
      launch { Semaphore.CarStationRG.serveCars() }
      launch { Semaphore.CarStationHE.serveCars() }
      launch { Semaphore.CarStationHG.serveCars() }
    } catch (e: Exception) {
      println("Error in station monitoring: ${e.message}")
    } finally {
      println("Scheduler: Station monitoring terminated.")
    }
  }
}
