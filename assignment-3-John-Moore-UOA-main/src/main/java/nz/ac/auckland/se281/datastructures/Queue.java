package nz.ac.auckland.se281.datastructures;

/** This is a custom implementation of a queue. */
public class Queue<T> {

  /** Private class for the what a node is. */
  private class Node<T> {
    private T val;
    private Node<T> next;

    /**
     * Contructor for a node.
     *
     * <p>A node will contain a value, and a pointer to the next node.
     *
     * @param v data the node will contain.
     */
    public Node(T v) {
      val = v;
      next = null;
    }
  }

  private int size;
  private Node<T> head;
  private Node<T> tail;

  /** Constructor, initialises the references. */
  public Queue() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  /**
   * Enqueue adds a new node to the end of the queue.
   *
   * @param data a value loaded into a new end node.
   */
  public void enqueue(T data) {
    // add data to new note at the tail

    // create new node
    Node<T> temp = new Node<T>(data);

    size++;
    // if first node
    if (this.tail == null) {
      this.head = this.tail = temp;
      return;
    }

    this.tail.next = temp;
    this.tail = temp;
  }

  /**
   * Dequeue takes a value from the front of the queue.
   *
   * <p>This process also removes the node from the queue.
   *
   * @return Generic data from the front of queue.
   */
  public T dequeue() {

    // // if the queue is empty
    if (this.head == null) {
      System.out.println("EMPTY QUEUE");
      return null; // ? can not happen perhaps??
    }

    // return the top, and remove it
    T out;
    size--;
    if (size <= 0) {
      out = this.head.val;
      this.head = this.tail = null;
    } else {
      out = this.head.val;
      this.head = this.head.next;
    }
    return out;
  }

  /**
   * Peek takes a value from the front of the queue.
   *
   * <p>This does not remove the node from the queue.
   *
   * @return Generic data from the front of queue
   */
  public T peek() {
    return this.head.val;
  }

  /**
   * This function checks if the queue is empty.
   *
   * @return boolean true if empty, else false.
   */
  public boolean isEmpty() {
    return this.head == null ? true : false;
  }

  /**
   * This function gets the size of the queue.
   *
   * @return integer, the size of the queue.
   */
  public int getSize() {
    return this.size;
  }
}
