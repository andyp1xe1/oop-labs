package lab3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

abstract class QueueContractTest<T> : StringSpec() {
  abstract fun createQueue(): Queue<T>
  init {
    "new queue should be empty" {
      val queue = createQueue()
      queue.isEmpty() shouldBe true
      queue.size() shouldBe 0
    }

    "enqueue should add elements to the queue" {
      val queue = createQueue()
      queue.enqueue(1 as T)
      queue.isEmpty() shouldBe false
      queue.size() shouldBe 1
    }

    "dequeue should remove elements in FIFO order" {
      val queue = createQueue()
      queue.enqueue(1 as T)
      queue.enqueue(2 as T)
      queue.dequeue() shouldBe 1 as T
      queue.dequeue() shouldBe 2 as T
      queue.isEmpty() shouldBe true
    }

    "dequeue from an empty queue should return null" {
      val queue = createQueue()
      queue.dequeue() shouldBe null
    }

    "peek from an empty queue should return null" {
      val queue = createQueue()
      queue.peek() shouldBe null
    }
  }
}

class ArrayQueueTest : QueueContractTest<Int>() {
  override fun createQueue(): Queue<Int> = ArrayQueue()
}

class LinkedListQueueTest : QueueContractTest<Int>() {
  override fun createQueue(): Queue<Int> = LinkedListQueue()
}

class CircularQueueTest : QueueContractTest<Int>() {
  override fun createQueue(): Queue<Int> = CircularQueue(10)
}
