package lab2

import coffee.*
import kotlin.io.println

fun main() {
  val coffee1 = SyrupCappuccino(mlOfSyrup = 10)
  println(coffee1.intensity.name)
  println(coffee1.name)
}
