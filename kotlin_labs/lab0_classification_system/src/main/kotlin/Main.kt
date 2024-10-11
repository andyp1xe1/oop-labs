package main

class Greeter() {
  fun greet(greeting: String, name: String) {
    println(greeting +  ", " + name + "!")
  }
}

fun main() {
  Greeter().greet("Hello there", "Obiwan")
}
