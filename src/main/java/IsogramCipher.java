/**
 * The isogram cipher takes an isogram (a word with no repeating letters) as the key, and replaces
 * each letter of the ciphertext with the following letter in the isogram. For example, the
 * isogram "TROUBLEMAKING" maps T->R, R->O, ..., N->G, G->T. ("GATE" becomes "TKRM")
 */

class IsogramCipher {
  static int SHIFT = 2;
  static String ISOGRAM = "subdermatoglyphic";
  private static String message = "Meet me at secret location at noon on Wednesday.".toLowerCase();

  public static void main(String[] args) {
    String ciphertext = encrypt(ISOGRAM, message, SHIFT);
    System.out.println("Ciphertext:\t\t" + ciphertext);

    String decrypted_plaintext = decrypt(ISOGRAM, ciphertext, SHIFT);
    System.out.println("Decrypted text:\t" + decrypted_plaintext);
  }

  static String decrypt(String isogram, String ciphertext, int shift) {
    return encrypt(new StringBuffer(isogram).reverse().toString(), ciphertext, shift);
  }

  static String encrypt(String isogram, String s, int shift) {
    StringBuilder sb = new StringBuilder();

    for (char c : s.toCharArray())
      sb.append(getShiftChar(isogram, c, shift));

    return sb.toString();
  }

  static char getShiftChar(String isogram, char c, int shift) {
    int charIndex = isogram.indexOf(c + "");

    if (0 <= charIndex) {
      int shiftCharIndex = (charIndex + shift) % isogram.length();
      char shiftChar = isogram.charAt(shiftCharIndex);
      return shiftChar;
    }

    return c;
  }
}
