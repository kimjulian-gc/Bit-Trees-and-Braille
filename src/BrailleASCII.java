import java.io.IOException;
import java.io.PrintWriter;
import resources.BrailleASCIITables;

public class BrailleASCII {
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "Invalid number of arguments: got " + args.length + ", needs 2");
    }

    Converter converter;
    switch (args[0].toLowerCase()) {
      case "braille":
        converter = BrailleASCII::convertToBraille;
        break;
      case "ascii":
        converter = BrailleASCII::convertToASCII;
        break;
      case "unicode":
        converter = BrailleASCII::convertToUnicode;
        break;
      default:
        throw new IllegalArgumentException("Invalid target character set: got " + args[0]);
    }

    PrintWriter pen = new PrintWriter(System.out, true);
    pen.println(converter.convert(args[1]));

  }

  public static String convertToBraille(String str) throws IOException {
    String output = "";
    for (char c : str.toCharArray()) {
      output += BrailleASCIITables.toBraille(c);
    }
    return output;
  }

  public static String convertToASCII(String bits) throws IOException {
    boolean isString = bits.length() > 6;
    if (!isString)
      return BrailleASCIITables.toASCII(bits);

    // bits is a string of braille
    String output = "";
    for (int i = 0; i < bits.length(); i += 6) {
      String subBits;
      if (i + 6 <= bits.length())
        subBits = bits.substring(i, i + 6);
      else
        subBits = bits.substring(i);
      output += BrailleASCIITables.toASCII(subBits);
    }
    return output;
  }

  public static String convertToUnicode(String str) throws IOException {
    String bits = convertToBraille(str);

    String output = "";
    for (int i = 0; i < bits.length(); i += 6) {
      String subBits;
      if (i + 6 <= bits.length())
        subBits = bits.substring(i, i + 6);
      else
        subBits = bits.substring(i);
      output += BrailleASCIITables.toUnicode(subBits);
    }
    return output;
  }

  @FunctionalInterface
  public interface Converter {
    String convert(String str) throws IOException;
  }

}
