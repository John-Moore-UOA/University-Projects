
// John Moore
// 171544055
// jmoo713
// === === === === ===
// Check list 
// --- Done ---
// Node Node type
// findNode 
// insertNode Naive with findNode
// display tree
// each node remembers height
// Rotation method
// Balance on insert
// change height on balance
// --- In progress ---
// regain sanity

public class AVLTree {

  class Node {
    // private fields
    Node right;
    Node left;
    Node parent; // easier to keep track of things

    int height;
    int key;

    public Node(int key) {
      this.right = null;
      this.left = null;
      this.key = key;
    }

    public int getkey() {
      return this.key;
    }

    public void setHeight(int height) {
      this.height = height;
    }

    public Node getLeft() {
      return this.left;
    }

    public Node getRight() {
      return this.right;
    }

    public Node getParent() {
      return this.parent;
    }

    public void setkey(int key) {
      this.key = key;
    }

    public void setleft(Node node) {
      this.left = node;
    }

    public void setright(Node node) {
      this.right = node;
    }

    public void setParent(Node node) {
      this.parent = node;
    }
  }

  private Node root;

  public int getHeight(Node node) {
    if (node == null) {
      return 0;
    }
    return node.height;
  }

  // constructor
  public AVLTree() {
    root = null;
  }

  // constructor but with a key
  public AVLTree(int key) {
    root = new Node(key);
  }

  public Node getRoot() {
    return this.root;
  }

  // public method to find node
  public Node findNode(int key) {
    return findNodeRecursive(root, key);
  }

  // find node in binary tree recursivly
  private Node findNodeRecursive(Node current, int key) {

    // if the current node is null
    if (current == null || current.getkey() == key) {
      return current;
    }

    // if key to search for is less than current node key
    if (key < current.getkey()) {

      // if left child is null
      if (current.getLeft() == null) {
        // return the node before the next node is null
        return current;
      }
      // recursivly run down the left branch
      return findNodeRecursive(current.getLeft(), key);

    } else {

      // if right child is null
      if (current.getRight() == null) {
        // return the node before the next node is null
        return current;
      }
      // recursivly run down the right branch
      return findNodeRecursive(current.getRight(), key);
    }
  }

  // displays values of tree like the normal display
  public void displayTree() {
    System.out.println("Displays Values:  root --> leaf\n");
    displayVerticalTree(root, "", false);
  }

  // displays heights of tree like the normal display
  private static void displayVerticalTree(Node node, String prefix, boolean isTail) {
    if (node == null) {
      return;
    }

    // i kinda yoinked this display
    String connector = isTail ? "└── " : "├── ";
    System.out.println(prefix + connector + node.getkey()); // display height

    if (node.getLeft() != null || node.getRight() != null) {
      String newPrefix = prefix + (isTail ? "    " : "│   ");
      displayVerticalTree(node.getLeft(), newPrefix, false);
      displayVerticalTree(node.getRight(), newPrefix, true);
    }
  }

  Node insert(Node node, int key) {

    // node is null, therefore root
    if (node == null)
      return (new Node(key));

    if (key < node.key) {
      node.left = insert(node.left, key);
    } else if (key > node.key) {
      node.right = insert(node.right, key);
    } else {
      return node;
    }

    // update height
    node.height = 1 + max(getHeight(node.left), getHeight(node.right));

    // check balancefactor
    int balance = balanceFactor(node);

    // I partially yoinked this code below, but I understand it after banging my
    // head against my keyboard for the last 8 hours

    // If this node becomes unbalanced, then there
    // are 4 cases Left Left Case
    if (balance > 1 && key < node.left.key)
      return rightRotate(node);

    // Right Right Case
    if (balance < -1 && key > node.right.key)
      return leftRotate(node);

    // Left Right Case
    if (balance > 1 && key > node.left.key) {
      node.left = leftRotate(node.left);
      return rightRotate(node);
    }

    // Right Left Case
    if (balance < -1 && key < node.right.key) {
      node.right = rightRotate(node.right);
      return leftRotate(node);
    }

    /* return the (unchanged) node pointer */
    return node;
  }

  // max helper function
  private int max(int a, int b) {
    return a > b ? a : b;
  }

  // returns the balanceFactor of a node
  private int balanceFactor(Node node) {
    if (node == null) {
      return 0;
    }

    // return the largest height between the two branches
    int leftHeight = node.getLeft() != null ? getHeight(node.getLeft()) : -1;
    int rightHeight = node.getRight() != null ? getHeight(node.getRight()) : -1;

    return leftHeight - rightHeight;
  }

  // slightly better node height calculation, basically isn't used in this code,
  // but I like this method so it will remain in here as dead code :)
  private void updateNodeHeights(Node node) {
    while (node != null) {
      int leftHeight = (node.getLeft() != null) ? getHeight(node.getLeft()) : -1;
      int rightHeight = (node.getRight() != null) ? getHeight(node.getRight()) : -1;
      node.setHeight(Math.max(leftHeight, rightHeight) + 1);
      node = node.getParent();
    }
  }

  // remove method
  Node remove(Node root, int key) {
    if (root == null)
      return root;

    // Perform standard BST delete
    // kinda nicer than using the findNode function
    if (key < root.key)
      root.left = remove(root.left, key);
    else if (key > root.key)
      root.right = remove(root.right, key);
    else {
      // Node with only one child or no child
      if ((root.left == null) || (root.right == null)) {
        Node temp = null;
        if (temp == root.left)
          temp = root.right;
        else
          temp = root.left;

        // No child case
        if (temp == null) {
          temp = root;
          root = null;
        } else // One child case
          root = temp; // Copy the contents of the non-empty child

        temp = null;
      } else {
        // Node with two children: Get the inorder successor (smallest in the right
        // subtree)
        Node temp = findMin(root.right);

        // Copy the inorder successor's data to this node
        root.key = temp.key;

        // Delete the inorder successor
        root.right = remove(root.right, temp.key);
      }
    }

    // If the tree had only one node, then return
    if (root == null)
      return root;

    // Update height of the current node
    root.height = max(getHeight(root.left), getHeight(root.right)) + 1;

    // Get the balance factor of this node
    int balance = balanceFactor(root);

    // If this node becomes unbalanced, then there are 4 cases

    // Left Left Case
    if (balance > 1 && balanceFactor(root.left) >= 0)
      return rightRotate(root);

    // Left Right Case
    if (balance > 1 && balanceFactor(root.left) < 0) {
      root.left = leftRotate(root.left);
      return rightRotate(root);
    }

    // Right Right Case
    if (balance < -1 && balanceFactor(root.right) <= 0)
      return leftRotate(root);

    // Right Left Case
    if (balance < -1 && balanceFactor(root.right) > 0) {
      root.right = rightRotate(root.right);
      return leftRotate(root);
    }

    return root;
  }

  // find the most min node from a node branch
  public Node findMin(Node node) {
    while (node.getLeft() != null) {
      node = node.getLeft();
    }
    return node;
  }

  // find the next largest node
  public Node findNextLarger(Node node) {
    if (node.getRight() != null) {
      return findMin(node.getRight());
    } else {
      while (node.getParent() != null && node.getParent().getLeft() != node) {
        node = node.getParent();
      }
      return node.getParent();
    }
  }

  Node rightRotate(Node y) {
    Node x = y.left;
    Node T2 = x.right;

    // Perform rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.height = max(getHeight(y.left), getHeight(y.right)) + 1;
    x.height = max(getHeight(x.left), getHeight(x.right)) + 1;

    // Return new root
    return x;
  }

  Node leftRotate(Node x) {
    Node y = x.right;
    Node T2 = y.left;

    // Perform rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.height = max(getHeight(x.left), getHeight(x.right)) + 1;
    y.height = max(getHeight(y.left), getHeight(y.right)) + 1;

    // Return new root
    return y;
  }

  public boolean contains(Node root, int key) {
    if (root == null)
      return false;

    if (key == root.key)
      return true;
    else if (key < root.key)
      return contains(root.left, key);
    else
      return contains(root.right, key);
  }

  public static void main(String[] args) {

    System.out.println("AVL tree demo");

    AVLTree tree = new AVLTree();

    int n = 100;
    for (int i = 0; i < n; i++) {
      tree.root = tree.insert(tree.root, i);
      tree.insert(tree.root, -i);
      System.out.println(i);
    }

    tree.displayTree();

    for (int i = 0; i < n; i++) {
      tree.contains(tree.root, n - 1);
      tree.contains(tree.root, -n + 1);
      System.out.println(i);
    }

    System.out.println("DONE");
    tree.displayTree();

    // int[] keys = { 3, 2, 1, 6, 1, 2, 4, 2, 7, 6, 1, 2, -1 };

    // for (int key : keys) {
    // tree.root = tree.insert(tree.getRoot(), key);
    // }
    // tree.displayTree(); // displays element keys
    // tree.remove(tree.root, 2);
    // tree.displayTree(); // displays element keys

    // System.out.println(tree.contains(tree.root, 6));
    // System.out.println(tree.contains(tree.root, 123));
    // System.out.println(tree.contains(tree.root, 0));
    // System.out.println(tree.contains(tree.root, 2));
    // tree.displayTreeHeights(); // displays heights of elements
    // tree.findMin(tree.findNode(15)); // find min element key from this
    // tree.findNextLarger(tree.findNode(15)); // findNode and find next largest
  }

}
