public class LinkedListDeque<T> {

    private class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }

    }

    private int size;
    private Node sentinel;

    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Creates a deep copy of other. */
    public LinkedListDeque(LinkedListDeque other) {
        this();
        size = other.size();

        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /**
     * add and remove operations must not involve any looping or recursion.
     * A single such operation must take “constant time”.
     * i.e. execution time should not depend on the size of the deque.
     */

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size = size + 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size = size + 1;
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = sentinel.next.item;
        sentinel.next.prev = null;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size = size - 1;

        return item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T item = sentinel.prev.item;
        sentinel.prev.next = null;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size = size - 1;

        return item;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size() == 0) ? true : false;
    }

    /**
     * Returns the number of items in the deque.
     * must take constant time.
    */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (Node ptr = sentinel.next; ptr != sentinel; ptr = ptr.next) {
            System.out.print(ptr.item + " ");
        }
        System.out.println();
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * Must not alter the deque!
     * use iteration, not recursion.
     */
    public T get(int index) {
        Node ptr = sentinel.next;
        while (ptr != sentinel && index != 0) {
            ptr = ptr.next;
            index = index - 1;
        }

        return ptr.item;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        return getHelper(sentinel.next, index);
    }

    private T getHelper(Node ptr, int index) {
        if (index == 0 || ptr == sentinel) {
            return ptr.item;
        }

        return getHelper(ptr.next, index - 1);
    }

}
