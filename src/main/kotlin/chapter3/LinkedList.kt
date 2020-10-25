package chapter3

class LinkedList<T> {
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var size = 0

    fun isEmpty(): Boolean {
        return size == 0
    }

    override fun toString(): String {
        return when(isEmpty()){
            true -> "Empty list"
            false -> head.toString()
        }
    }

    fun push(value: T): LinkedList<T> {
        head = Node(value = value, next = head)
        if(tail == null){
            tail = head;
        }
        size++
        return this
    }

    fun append(value: T){
        if(isEmpty()){
            push(value)
            return
        }

        tail?.next = Node(value = value)

        tail = tail?.next
        size++
    }

    fun nodeAt(index: Int):Node<T>? {
        var currentNode = head
        var currentIndex = 0

        while(currentNode != null && currentIndex < index){
            currentNode = currentNode.next
            currentIndex++
        }
        return currentNode
    }

    fun insert(value: T, afterNode: Node<T>):Node<T>{
        if(tail == afterNode){
            append(value)
            return tail!!
        }

        var newNode = Node(value = value, next = afterNode.next)

        afterNode.next = newNode
        size++
        return newNode
    }

    fun pop(): T? {
        if(!isEmpty()) size--
        val result = head?.value
        head = head?.next

        if(isEmpty()){
            tail = null
        }
        return result
    }

    fun removeLast(): T? {
        val head = head ?: return null
        if(head.next == null) return pop()
        size--

        var prev = head
        var current = head

        var next = current.next
        while(next != null){
            prev = current
            current = next
            next = current.next
        }

        prev.next = null
        tail = prev
        return current.value
    }

    fun removeAfter(node: Node<T>): T?{
        val result = node.next?.value

        if(node.next == tail){
            tail = node
        }

        if(node.next != null){
            size--
        }

        node.next = node.next?.next
        return result
    }
}
