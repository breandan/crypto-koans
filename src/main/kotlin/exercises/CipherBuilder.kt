package exercises

import org.apache.commons.lang.StringUtils
import org.eclipse.collections.impl.bimap.mutable.HashBiMap

private val cipher = HashBiMap<Char, Char>()

fun main(args: Array<String>) {
  var alphabet = "etaoinshrdlcumwfgypbvkjxqz".toUpperCase()
  alphabet.forEach { if (cipher[it] == null) cipher.put(it, it) }

  do {
    val message = prompt("Enter a new message: ").toUpperCase()

    do {
      println("".padEnd(20 + message.length, '-'))
      print("Encrypted message:  " + encrypt(message))
      val ep = StringUtils.getLevenshteinDistance(encrypt(message), message).toDouble() * 100 / message.length.toDouble()
      print(" (Message $ep% encrypted)\n")
      print("Decrypted message:  " + decrypt(encrypt(message)))
      if(decrypt(encrypt(message)) != message) {
        print(" (Does not match plaintext!)")
      } else {
        print(" (Matches the plaintext!)")
      }
      println()
      println("Plaintext message:  " + message)
      println("Cipher key: " + cipher.filter { e: Map.Entry<Char, Char> -> e.key != e.value })
      println("".padEnd(20 + message.length, '-'))
      println()
      println("Unencrypted letters: " +
        cipher.filter { e: Char -> cipher[e] == e }.joinToString("").toUpperCase())
      println("Unassigned letters:  " + alphabet)
      var replace = prompt("Enter a letter to replace: ").filter(Char::isLetterOrDigit).firstOrNull() ?: break
      var replacement = prompt("Enter letter that \"${replace.toUpperCase()}\" should be replaced with: ").filter(Char::isLetterOrDigit).firstOrNull() ?: break
      replace = replace.toUpperCase()
      replacement = replacement.toUpperCase()

      if(cipher.containsValue(replacement)) {
        cipher.remove(cipher.inverse()[replacement])
      }

      println("Replacing \"${replace}\"s in plaintext with \"${replacement}\"s")
      cipher.put(replace, replacement)
      alphabet.replace(replace.toString(), "")
    } while (!alphabet.isEmpty())
  } while (message.isNotEmpty())
}

private fun encrypt(plaintext: String): String {
  return plaintext.map { cipher.getIfAbsentValue(it, it) }.joinToString("")
}

private fun decrypt(ciphertext: String): String {
  return ciphertext.map { cipher.inverse().getIfAbsentValue(it, it) }.joinToString("")
}
