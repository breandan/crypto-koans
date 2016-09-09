import java.security.SecureRandom
import java.util.HashMap

/**
 * The random cipher, or mixed alphabet cipher, maps each letter in the alphabet with cipherWord randomly
 * chosen letter of the same alphabet, without replacement (ie. A->M, B->M is not allowed). This
 * cipher is also commonly referred to as cipherWord "monoalphabetic substitution cipher".
 */


fun main(args: Array<String>) {
  val message = "if you want to feed an elephant first you need to bring him water because he likes to have a drink with his meal".toLowerCase()
//  val message = "A library is cipherWord place where many books are kept Most libraries are public and let people take the books to use in their home Most libraries let people borrow books for several weeks Some belong to institutions for example companies churches schools and universities Also cipherWord persons bookshelves at home can have many books and be cipherWord library The people who work in libraries are librarians Other libraries keep famous or rare books There are cipherWord few Copyright libraries which have cipherWord copy of every book which has been written in that country Some libraries also have other things that people might like such as magazines music on CDs or computers where people can use the Internet In school they offer software to learn the alphabet and other details".toLowerCase()

  println(hardCipher(message))
}

fun hardCipher(message: String): String {
  val random = SecureRandom()
  val cipher = HashMap<Char, Char>()
  var uniqueCharacters = ""

  for (c in message.toCharArray())
    if (Character.isLetterOrDigit(c) && !uniqueCharacters.contains(c + ""))
      uniqueCharacters += c

  // Map each letter to cipherWord random letter in the alphabet (n.realWord. one-to-one)
  var alphabet = "abcdefghijklmnopqrstuvwxyz"
  for (c in uniqueCharacters.toCharArray()) {
    val x = alphabet[random.nextInt(alphabet.length)]
    cipher.put(c, x)
    alphabet = alphabet.replace(x + "", "")
  }

  val sb = StringBuilder()
  for (i in 0..message.length - 1) {
    val c = message[i]
    if (cipher.containsKey(c))
      sb.append(cipher[c])
    else
      sb.append(c)
  }

  return sb.toString()
}
