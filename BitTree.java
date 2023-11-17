import java.io.InputStream;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

class BitTree {
  int bitLength;
  BitTreeNode root;

  /**
   * builds a tree that will store mappings from strings of n bits to strings.
   */
  public BitTree(int n) {
    this.bitLength = n;
  }

  /**
   * follows the path through the tree given by bits (adding nodes as appropriate) and adds or
   * replaces the value at the end with value. set should throw an exception if bits is the
   * inappropriate length or contains values other than 0 or 1.
   */
  void set(String bits, String value) throws IllegalArgumentException {
    checkBitString(bits);

    this.root = set(this.root, bits, value);
  }

  /**
   * follows the path through the tree given by bits, returning the value at the end. If there is no
   * such path, or if bits is the incorrect length, get should throw an exception.
   */
  String get(String bits) throws IllegalArgumentException {
    checkBitString(bits);

    try {
      return get(this.root, bits);
    } catch (NoSuchElementException e) {
      throw new NoSuchElementException("Could not locate value at " + bits);
    }
  }

  /**
   * prints out the contents of the tree in CSV format.
   */
  void dump(PrintWriter pen) {
    printNode(pen, this.root, "");
  }

  /**
   * reads a series of lines of the form bits,value and stores them in the tree.
   */
  void load(InputStream source) {

  }

  class BitTreeNode {
    BitTreeNode left;
    BitTreeNode right;
  }

  class BitTreeLeaf extends BitTreeNode {
    String value;

    BitTreeLeaf(String value) {
      this.value = value;
    }
  }

  BitTreeNode set(BitTreeNode currNode, String remainingBits, String value)
      throws IllegalArgumentException {
    if (remainingBits.length() == 0) {
      // we are at the leaf
      return new BitTreeLeaf(value);
    }

    // we have bits remaining
    BitTreeNode toEdit = currNode;
    if (toEdit == null) {
      // current node didn't exist, we need to make a new node
      toEdit = new BitTreeNode();
    }

    char path = getPath(remainingBits);

    // bit string is valid, path is valid. edit the node
    String newRemaining = remainingBits.substring(1);
    if (path == '0') {
      // go left
      toEdit.left = set(toEdit.left, newRemaining, value);
    } else {
      // go right
      toEdit.right = set(toEdit.right, newRemaining, value);
    }

    // return edited node
    return toEdit;
  }

  String get(BitTreeNode currNode, String remainingBits)
      throws NoSuchElementException, IllegalArgumentException {
    if (remainingBits.length() == 0) {
      if (!(currNode instanceof BitTreeLeaf)) {
        throw new NoSuchElementException();
      }
      return ((BitTreeLeaf) currNode).value;
    }

    // we have bits remaining
    char path = getPath(remainingBits);

    // bit string is valid, path is valid.
    String newRemaining = remainingBits.substring(1);
    if (path == '0') {
      // go left
      return get(currNode.left, newRemaining);
    }
    // go right otherwise
    return get(currNode.right, newRemaining);
  }

  void checkBitString(String bits) throws IllegalArgumentException {
    if (bits.length() != this.bitLength) {
      throw new IllegalArgumentException("Inappropriate length for bit string: should be length "
          + this.bitLength + ", got " + bits.length());
    }
  }

  char getPath(String bits) throws IllegalArgumentException {
    char path = bits.charAt(0);
    if (path != '0' && path != '1') {
      // bit string is invalid
      throw new IllegalArgumentException(
          "Bit string contains values other than 0 or 1: encountered " + path);
    }
    return path;
  }

  void printNode(PrintWriter pen, BitTreeNode node, String currentBitString) {
    if (node == null)
      return;
    if (node instanceof BitTreeLeaf) {
      pen.println(currentBitString + "," + ((BitTreeLeaf) node).value);
      return;
    }

    printNode(pen, node.left, currentBitString + "0");
    printNode(pen, node.right, currentBitString + "1");
  }
}
