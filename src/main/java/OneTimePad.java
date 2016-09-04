import java.security.SecureRandom;

class OneTimePad {
  private static SecureRandom secureRandom = new SecureRandom();
  private static String message = "Meet me at secret location at noon on Wednesday.";
  private static int ALPHABET_SIZE = 94; //ASCII 32 - 126
  private static int PAD_SIZE = 100;

  public static void main(String[] args) {
    final char[] plaintext = message.toCharArray();

    // Generate secure random key (never reuse this)
    final int[] pad = new int[PAD_SIZE];
    for (int i = 0; i < pad.length; i++) {
      pad[i] = secureRandom.nextInt(ALPHABET_SIZE);
    }

    // Combine the key with the plaintext
    char[] ciphertext = new char[PAD_SIZE];
    for (int i = 0; i < PAD_SIZE; i++) {
      if (i < plaintext.length)
        ciphertext[i] = getShiftChar(plaintext[i], pad[i]);
      else
        ciphertext[i] = getShiftChar(' ', pad[i]);
    }

    System.out.println("Ciphertext: " + new String(ciphertext));

    // Separate the key from the ciphertext
    char[] decrypted = new char[PAD_SIZE];
    for (int i = 0; i < PAD_SIZE; i++) {
      decrypted[i] = getShiftChar(ciphertext[i], -pad[i]);
    }

    System.out.println("Decrypted text: " + new String(decrypted));
  }

  static char getShiftChar(char c, int shift) {
    int x = c + shift - 32;
    int z = Math.floorMod(x, ALPHABET_SIZE);
    char i = (char) (z + 32);
    return i;
  }
}
