package exercises

import org.eclipse.collections.impl.bimap.mutable.HashBiMap

private val plainToCipher = HashBiMap<Char, Char>()

fun main(args: Array<String>) {
  var alphabet = "etaoinshrdlcumwfgypbvkjxqz".toUpperCase()
  alphabet.forEach { if (plainToCipher[it] == null) plainToCipher.put(it, it) }

  do {
    val plaintext = prompt("Enter a new message: ").toUpperCase()

    do {
      println("".padEnd(20 + plaintext.length, '-'))
      val ciphertext = encrypt(plaintext)

      print("Encrypted message:  " + ciphertext)

      val cipherLetters = ciphertext.mapIndexed { i, c -> if (plaintext[i] == c) 0 else 1 }.sum()
      val percentEncrypted = cipherLetters.toDouble() * 100 / ciphertext.length.toDouble()

      print(" (Message $percentEncrypted% encrypted)\n")
      print("Decrypted message:  " + decrypt(ciphertext))

      if (decrypt(encrypt(plaintext)) != plaintext) {
        print(" (Does not match plaintext!)")
      } else {
        print(" (Matches the plaintext!)")
      }

      println()
      println("Plaintext message:  " + plaintext)
      println("Cipher key: " + plainToCipher.filter { e: Map.Entry<Char, Char> -> e.key != e.value })
      println("".padEnd(20 + plaintext.length, '-'))
      println()
      val unencrypted = alphabet.filter { plainToCipher.contains(it) && plainToCipher[it] != it }.toList()
      println("Unencrypted letters: " + unencrypted.joinToString { "" }.toUpperCase())
      println("Unassigned letters:  " + alphabet)

      var replace = prompt("Enter a letter to replace: ").filter(Char::isLetterOrDigit).firstOrNull() ?: break
      var replacement = prompt("Enter letter that \"${replace.toUpperCase()}\" should be replaced with: ").filter(Char::isLetterOrDigit).firstOrNull() ?: break
      replace = replace.toUpperCase()
      replacement = replacement.toUpperCase()

      if (plainToCipher.containsValue(replacement)) { // If the cipher letter has already been mapped to
        val previousKey = (plainToCipher.inverse()[replacement])
//        plainToCipher.remove(previousKey) // Remove the previous plain - cipher entry
        plainToCipher.put(previousKey, previousKey)
      }

      println("Replacing \"${replace}\"s in plaintext with \"${replacement}\"s")
      plainToCipher.put(replace, replacement)
      alphabet = alphabet.replace(replacement.toString(), "")
    } while (!alphabet.isEmpty())
  } while (plaintext.isNotEmpty())
}

private fun encrypt(plaintext: String): String {
  return plaintext.map { plainToCipher.getIfAbsentValue(it, it) }.joinToString("")
}

private fun decrypt(ciphertext: String): String {
  return ciphertext.map { plainToCipher.inverse().getIfAbsentValue(it, it) }.joinToString("")
}