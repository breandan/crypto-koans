class CaesarCipher {
  private static String message = "meet me at secret location next wednesday afternoon";
  private static String[] dictionary = {"secret"};
  private static int secret = 6;

  public static void main(String[] args) {
    String ciphertext = encrypt(message, secret);
    System.out.println("Ciphertext: " + ciphertext);
    System.out.println("Brute forcing key... ");
    for (int i = 0; i > -26; i--) {
      String p = decrypt(ciphertext, i);
      System.out.println(p);

      for (String s : dictionary) {
        if (p.contains(s)) {
          System.out.println("Plaintext found! Terminating...");
          return;
        }
      }
    }
  }

  private static String decrypt(String ciphertext, int key) {
    return encrypt(ciphertext, -key);
  }

  private static String encrypt(String string, int key) {
    StringBuilder sb = new StringBuilder();

    for (char c : string.toCharArray()) {
      if (Character.isLetter(c))
        sb.append(getShiftChar(c, key));
      else
        sb.append(c);
    }

    return sb.toString();
  }

  static char getShiftChar(char c, int shift) {
    int x = c + shift;
    int y = x - 97;
    int z = Math.floorMod(y, 26);
    char i = (char) (z + 97);
    return i;
  }
}
