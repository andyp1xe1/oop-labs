package main

data class Display(val width: Int, val height: Int, val ppi: Float, val model: String) {

  fun compareSize(other: Display): String {
    val thisArea = width * height
    val otherArea = other.width * other.height

    return if (thisArea > otherArea) {
      "$model is larger than ${other.model}."
    } else if (thisArea < otherArea) {
      "$model is smaller than ${other.model}."
    } else {
      "$model and ${other.model} have the same size."
    }
  }

  fun compareSharpness(other: Display): String {
    return when {
      this.ppi > other.ppi -> "$model is sharper than ${other.model}."
      this.ppi < other.ppi -> "$model is less sharp than ${other.model}."
      else -> "$model and ${other.model} have the same sharpness."
    }
  }

  fun compareWithMonitor(other: Display): String {
    val sizeComparison = compareSize(other)
    val sharpnessComparison = compareSharpness(other)
    return "$sizeComparison\n$sharpnessComparison"
  }
}

fun main() {
  val display1 = Display(width = 1920, height = 1080, ppi = 90.0f, model = "Display 1")
  val display2 = Display(width = 2560, height = 1440, ppi = 110.0f, model = "Display 2")
  val display3 = Display(width = 3840, height = 2160, ppi = 200.0f, model = "Display 3")

  println(display1.compareSize(display2))
  println(display1.compareSharpness(display3))
  println(display2.compareWithMonitor(display3))
}
