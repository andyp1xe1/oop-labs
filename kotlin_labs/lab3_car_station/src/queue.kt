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
