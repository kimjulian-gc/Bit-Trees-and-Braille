import java.io.InputStream;
import java.io.PrintWriter;

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
    if (bits.length() != this.bitLength) {
      throw new IllegalArgumentException("Inappropriate length for bit string: should be length "
          + this.bitLength + ", got " + bits.length());
    }

    this.root = set(this.root, bits, value);
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

    char path = remainingBits.charAt(0);
    if (path != '0' && path != '1') {
      // bit string is invalid
      throw new IllegalArgumentException("Bit string contains values other than 0 or 1: encountered " + path);
    }

    // bit string is valid, path is valid. edit the node
    String newRemaining = remainingBits.substring(1);
    if (path == '0') {
      // go left
      toEdit.left = set(toEdit.left, newRemaining, value);
    }
    if (path == '1') {
      // go right
      toEdit.right = set(toEdit.right, newRemaining, value);
    }

    // return edited node
    return toEdit;
  }

  /**
   * follows the path through the tree given by bits, returning the value at the end. If there is no
   * such path, or if bits is the incorrect length, get should throw an exception.
   */
  String get(String bits) {
    // stub
    return "";
  }

  /**
   * prints out the contents of the tree in CSV format.
   */
  void dump(PrintWriter pen) {
    printNode(pen, this.root, "");
  }

  void printNode(PrintWriter pen, BitTreeNode node, String currentBitString) {
    if (node == null) return;
    if (node instanceof BitTreeLeaf) {
      pen.println(currentBitString + "," + ((BitTreeLeaf) node).value);
      return;
    }

    printNode(pen, node.left, currentBitString + "0");
    printNode(pen, node.right, currentBitString + "1");
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
}
