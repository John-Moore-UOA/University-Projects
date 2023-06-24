package nz.ac.auckland.se281.datastructures;

/** Class for the Stack data strucutre. */
public class Stack<T> {

  private class Node<T> {

    private Node<T> next;
    private T val;

    /**
     * Contructor for a node.
     *
     * <p>A node contains data, and a reference to the next node.
     *
     * @param data the data that will be contained by the node.
     */
    public Node(T data) {
      this.next = null;
      this.val = data;
    }

    /**
     * getData returns the data contained in the instance of the node.
     *
     * @return data contained in node.
     */
    public T getData() {
      return this.val;
    }
  }

  private int size;
  private Node<T> head;

  /** Contructor initailises the head pointer and the size. */
  public Stack() {
    this.head = null;
    this.size = 0;
  }

  /**
   * Push, adds a new node to the head/top of the stack.
   *
   * @param data data that will be loaded into a new node.
   */
  public void push(T data) {
    // push to the top of the stack
    Node<T> temp = new Node<T>(data);

    temp.next = this.head;

    this.head = temp;

    size++;
  }

  /**
   * Pop, removes the head/top node from the stack.
   *
   * @return Generic data, the data that was at the top/head of the stack.
   */
  public T pop() {
    // pop from the top of the stack

    if (isEmpty()) {
      return null;
    }

    T data = this.head.getData();
    // set next as the head
    size--;

    if (size == 0) {
      this.head = null;
    } else {
      this.head = this.head.next;
    }

    return data;
  }

  /**
   * Peek, takes the head/top node from the stack, without remove it.
   *
   * @return Generic data, the data that was at the top/head of the stack.
   */
  public T peek() {
    if (this.head == null) {
      return null;
    }
    return this.head.getData();
  }

  /**
   * Size, gets the size of the stack.
   *
   * @return int, the size of the stack.
   */
  public int size() {
    return size;
  }

  /**
   * isEmpty checks if the stack is empty.
   *
   * @return boolean, true if the stack is empty, else return false.
   */
  public boolean isEmpty() {
    return this.head == null;
  }
}
