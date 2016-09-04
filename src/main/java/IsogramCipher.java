import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by breandan on 9/4/16.
 */
public class IsogramCipher {
  static String ISOGRAM = "thequickbrownfoxjumpedoverthelazydog";
  private static String message = "Meet me at secret location at noon on Wednesday.".toLowerCase();

  public static void main(String[] args) {
    StringBuilder sb = new StringBuilder();
    for (Character c : message.toCharArray()) {
      if(ISOGRAM.contains(c + ""))
        sb.append(ISOGRAM.charAt((ISOGRAM.indexOf(c + "") + 1 )% (ISOGRAM.length() - 1)));
      else
        sb.append(c);
    }
    System.out.println(sb);
  }
}
