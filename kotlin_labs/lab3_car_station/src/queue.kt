interface Queue<T> {
  fun enqueue(element: T)
  fun dequeue(): T?
  fun peek(): T?
  fun isEmpty(): Boolean
  fun size(): Int
}

class ArrayQueue<T> : Queue<T> {
  private val elements = mutableListOf<T>()

  override fun enqueue(element: T) {
    elements.add(element)
  }

  override fun dequeue(): T? {
    return if (elements.isNotEmpty()) elements.removeAt(0) else null
  }

  override fun peek(): T? {
    return elements.firstOrNull()
  }

  override fun isEmpty(): Boolean {
    return elements.isEmpty()
  }

  override fun size(): Int {
    return elements.size
  }
}

class LinkedListQueue<T> : Queue<T> {
  private data class Node<T>(val value: T, var next: Node<T>? = null)

  private var head: Node<T>? = null
  private var tail: Node<T>? = null
  private var count = 0

  override fun enqueue(element: T) {
    val newNode = Node(element)
    if (tail == null) {
      head = newNode
      tail = newNode
    } else {
      tail?.next = newNode
      tail = newNode
    }
    count++
  }

  override fun dequeue(): T? {
    val dequeuedValue = head?.value
    if (head != null) {
      head = head?.next
      if (head == null) tail = null
      count--
    }
    return dequeuedValue
  }

  override fun peek(): T? {
    return head?.value
  }

  override fun isEmpty(): Boolean {
    return count == 0
  }

  override fun size(): Int {
    return count
  }
}

class CircularQueue<T>(private val capacity: Int) : Queue<T> {
  private val elements: MutableList<T?> = MutableList(capacity) { null }
  private var front: Int = -1
  private var rear: Int = -1

  private fun isFull(): Boolean {
    return (rear + 1) % capacity == front
  }

  override fun isEmpty(): Boolean {
    return front == -1
  }

  override fun enqueue(element: T) {
    if (isFull()) {
      throw IllegalStateException("Queue is full")
    }

    if (isEmpty()) {
      front = 0
    }

    rear = (rear + 1) % capacity
    elements[rear] = element
  }

  override fun dequeue(): T? {
    if (isEmpty()) {
      return null
    }

    val item = elements[front]
    elements[front] = null

    if (front == rear) {
      // Queue becomes empty
      front = -1
      rear = -1
    } else {
      front = (front + 1) % capacity
    }

    return item
  }

  override fun peek(): T? {
    return if (isEmpty()) {
      null
    } else {
      elements[front]
    }
  }

  override fun size(): Int {
    if (isEmpty()) return 0
    return if (rear >= front) {
      rear - front + 1
    } else {
      capacity - front + rear + 1
    }
  }
}
