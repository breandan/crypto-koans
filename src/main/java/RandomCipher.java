import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class RandomCipher {
  private static String message = "Meet me at secret location at noon on Wednesday.".toLowerCase();
  private static Random random = new Random();

  public static void main(String[] args) {
    System.out.println(hardCipher());
  }

  static String hardCipher() {
    Map<Character, Character> cipher = new HashMap<Character, Character>();
    String uniqueCharacters = "";

    for (char c : message.toCharArray())
      if (Character.isLetterOrDigit(c) && !uniqueCharacters.contains(c + ""))
        uniqueCharacters += c;

    // Map each letter to a random letter in the alphabet (n.b. one-to-one)
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    for (char c : uniqueCharacters.toCharArray()) {
      char x = alphabet.charAt(random.nextInt(alphabet.length()));
      cipher.put(c, x);
      alphabet = alphabet.replace(x + "", "");
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      char c = message.charAt(i);
      if (cipher.containsKey(c))
        sb.append(cipher.get(c));
      else
        sb.append(c);
    }

    return sb.toString();
  }
}
