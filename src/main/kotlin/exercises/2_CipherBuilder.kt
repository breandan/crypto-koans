package exercises

import org.eclipse.collections.impl.bimap.mutable.HashBiMap

private val plainToCipher = HashBiMap<Char, Char>()

fun main() {
  val alphabet = "etaoinshrdlcumwfgypbvkjxqz".uppercase()
  alphabet.forEach { if (plainToCipher[it] == null) plainToCipher[it] = it }

  do {
    val plaintext = prompt("Enter a new message: ").uppercase()

    do {
      println("".padEnd(20 + plaintext.length, '-'))
      val ciphertext = encrypt(plaintext)

      print("Encrypted message:  $ciphertext")

      val cipherLetters = ciphertext.mapIndexed { i, c -> if (plaintext[i] == c || !c.isLetter()) 0 else 1 }.sum()
      val percentEncrypted = cipherLetters.toDouble() * 100 / ciphertext.length.toDouble()

      print(" (Message $percentEncrypted% encrypted)\n")
      print("Decrypted message:  " + decrypt(ciphertext))

      if (decrypt(encrypt(plaintext)) != plaintext) print(" (Does not match plaintext!)")
      else print(" (Matches the plaintext!)")

      println()
      println("Plaintext message:  $plaintext")
      println("Key (plain=cipher): " + plainToCipher.filter { e: Map.Entry<Char, Char> -> e.key != e.value })
      println("".padEnd(20 + plaintext.length, '-'))
      println()

      val unencrypted = getUnencryptedChars(alphabet)
      println("Unencrypted letters: " + unencrypted.joinToString("").uppercase())
      val available = getAvailableChars(alphabet)
      println("Unassigned letters:  " + available.joinToString("").uppercase())

      var replace: Char = prompt("Enter a letter to replace: ").firstOrNull(Char::isLetterOrDigit) ?: break
      var replacement: Char = prompt("Enter letter that \"${replace.uppercase()}\" should be replaced with: ")
        .firstOrNull(Char::isLetterOrDigit) ?: break
      replace = replace.uppercaseChar()
      replacement = replacement.uppercaseChar()

      println("Replacing \"$replace\"s in plaintext with \"$replacement\"s...")
      if (plainToCipher[replace] != null) {
        plainToCipher.remove(replace)
      }

      resetValue(replacement)

      plainToCipher[replace] = replacement
    } while (available.isNotEmpty())
  } while (plaintext.isNotEmpty())
}

private fun resetValue(cipherChar: Char) {
  if (plainToCipher.containsValue(cipherChar)) {
    val previousKey = plainToCipher.inverse()[cipherChar]!!
    println("\"$previousKey\" was previously mapped to \"$cipherChar\", removing {$previousKey=$cipherChar} ")
    plainToCipher.remove(previousKey) // Remove the previous plain - cipher entry
//    if (previousKey != cipherChar) {
//      resetValue(previousKey)
//      plainToCipher[previousKey] = previousKey
//    }
  }
}

// Unencrypted chars are alphabetic characters that are absent or match their encrypted character
private fun getUnencryptedChars(alphabet: String) =
  alphabet.toCharArray().filter { plainToCipher[it] == null || plainToCipher[it] == it }.toList()

// Available chars are alphabetic characters that are absent or are identical to their plaintext mapping
private fun getAvailableChars(alphabet: String) =
  alphabet.toCharArray().filter { !plainToCipher.containsValue(it) || plainToCipher.inverse()[it] == it }.toList()

private fun encrypt(plaintext: String): String =
  plaintext.map { plainToCipher.getIfAbsentValue(it, it) }.joinToString("")

private fun decrypt(ciphertext: String): String =
  ciphertext.map { plainToCipher.inverse().getIfAbsentValue(it, it) }.joinToString("")