package resources;

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
  public static String toBraille(char letter) throws IOException {
    if (BrailleASCIITables.asciiToBrailleTree == null) {
      BrailleASCIITables.asciiToBrailleTree = new BitTree(8);
      asciiToBrailleTree.load(new FileInputStream(new File("src/resources/ASCIIToBraille.csv")));
    }

    return asciiToBrailleTree
        .get(String.format("%8s", Integer.toBinaryString(letter)).replace(' ', '0'));
  }

  /**
   * converts a string of bits representing a braille character to the corresponding ASCII
   * character.
   */
  public static String toASCII(String bits) throws IOException {
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
  public static char toUnicode(String bits) throws IOException {
    if (BrailleASCIITables.brailleToUnicodeTree == null) {
      BrailleASCIITables.brailleToUnicodeTree = new BitTree(6);
      brailleToUnicodeTree
          .load(new FileInputStream(new File("src/resources/BrailleToUnicode.csv")));
    }

    return Character.toChars(Integer.parseInt(brailleToUnicodeTree.get(bits), 16))[0];
  }
}
