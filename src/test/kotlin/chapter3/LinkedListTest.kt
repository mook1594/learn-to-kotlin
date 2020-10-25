package chapter3

class LinkedListTest {


    fun <T> LinkedList<T>.getMiddle(): Node<T>? {
        var slow = this.nodeAt(0)
        var fast = this.nodeAt(0)

        while(fast != null){
            fast = fast.next
            if(fast != null){
                fast = fast.next
                slow = slow?.next
            }
        }
        return slow
    }
}
