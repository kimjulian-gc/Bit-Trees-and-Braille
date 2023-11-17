import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import structs.BitTree;

public class BrailleASCIITables {
  static BitTree asciiToBrailleTree = null;
  static BitTree brailleToAsciiTree = null;
  static BitTree brailleToUnicodeTree = null;

  /**
   * converts an ASCII character to a string of bits representing the corresponding braille
   * character.
   */
  static String toBraille(char letter) throws IOException {
    if (BrailleASCIITables.asciiToBrailleTree == null) {
      BrailleASCIITables.asciiToBrailleTree = new BitTree(8);
      asciiToBrailleTree.load(new FileInputStream(new File("src/resources/ASCIIToBraille.csv")));
    }
    
    return asciiToBrailleTree.get(Integer.toBinaryString(letter));
  }

  /**
   * converts a string of bits representing a braille character to the corresponding ASCII
   * character.
   */
  static String toASCII(String bits) throws IOException {
    if (BrailleASCIITables.brailleToAsciiTree == null) {
      BrailleASCIITables.brailleToAsciiTree = new BitTree(6);
      brailleToAsciiTree.load(new FileInputStream(new File("src/resources/BrailleToASCII.csv")));
    }

    return brailleToAsciiTree.get(bits);
  }

  /**
   * converts a string of bits representing a braille character to the corresponding Unicode braille
   * character for those bits. Only supports six-bit braille characters.
   */
  static String toUnicode(String bits) throws IOException {
    if (BrailleASCIITables.brailleToUnicodeTree == null) {
      BrailleASCIITables.brailleToUnicodeTree = new BitTree(6);
      brailleToUnicodeTree.load(new FileInputStream(new File("src/resources/BrailleToUnicode.csv")));
    }

    return brailleToUnicodeTree.get(bits);
  }
}
