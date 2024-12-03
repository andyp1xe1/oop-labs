package lab3

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength

class MyTest : FunSpec() {
  init {
    test("first test") {
      val name = "car station"
      name.shouldHaveLength(11)
    }
  }
}
