public class ArrayDeque<T> {

    /**
     * 1) add and remove must take constant time, except during resizing operations.
     * 2) The starting size of your array should be 8.
     */

    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    private double threshold = 0.25;
    private int factor = 2;

    /** Creates an empty linked list deque. */
    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        items = (T[]) new Object[8];
    }

    /** Creates a deep copy of other. */
    public ArrayDeque(ArrayDeque other) {
        size = other.size();
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        items = (T[]) new Object[other.size()];
        System.arraycopy(other.items, 0, items, 0, size);
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (isFull()) {
            resize(capacity() * factor);
        }

        items[nextFirst] = item;
        nextFirst = updateMark(nextFirst - 1);
        size = size + 1;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item) {
        if (isFull()) {
            resize(capacity() * factor);
        }

        items[nextLast] = item;
        nextLast = updateMark(nextLast + 1);
        size = size + 1;
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        T item = getFirst();

        items[first()] = null;
        nextFirst = first();
        size = size - 1;

        if (isSparse(threshold)) {
            resize(capacity() / factor);
        }

        return item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        T item = getLast();

        items[last()] = null;
        nextLast = last();
        size = size - 1;

        if (isSparse(threshold)) {
            resize(capacity() / factor);
        }

        return item;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];

        for(int i = 0; i < size(); i++) {
            newItems[i] = get(i);
        }

        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    /** Returns true if deque is full, false otherwise. */
    public boolean isFull() {
        return (size() == items.length) ? true : false;
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

    /** Returns the array's length. */
    public int capacity() {
        return items.length;
    }

    private int first() {
        return updateMark(nextFirst + 1);
    }

    private int last() {
        return updateMark(nextLast - 1);
    }

    private T getFirst() {
        return items[first()];
    }

    private T getLast() {
        return items[last()];
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * Must not alter the deque!
     * must take constant time.
     */
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        return items[updateMark(nextFirst + 1 + index)];
    }

    private boolean isSparse(double limitRatio) {
        return (capacity() >= 16 && usageRatio() < threshold) ? true : false;
    }

    private double usageRatio() {
        return 1.0 * size / items.length;
    }

    private int updateMark(int mark) {
        if (mark < 0) {
            mark = capacity() + mark;
        }
        return mark % capacity();
    }
}
