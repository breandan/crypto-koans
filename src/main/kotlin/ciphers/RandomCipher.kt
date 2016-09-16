package ciphers

import org.eclipse.collections.impl.bimap.mutable.HashBiMap
import java.security.SecureRandom

/**
 * The random cipher, or mixed alphabet cipher, maps each letter in the
 * alphabet with a randomly chosen letter of the same alphabet, without
 * replacement (ie. A->M, B->M is not allowed). This cipher is also commonly
 * referred to as a "monoalphabetic substitution cipher".
 */

fun main(args: Array<String>) {
  val message = "four score and seven years ago our fathers".toLowerCase()

  println("Plaintext:  " + message)
  val ciphertext = encrypt(message)
  println("Ciphertext: " + ciphertext)
  println("Decrypted:  " + decrypt(ciphertext))
}

val cipher = HashBiMap<Char, Char>()

private fun encrypt(plaintext: String): String {
  val random = SecureRandom()
  var uniqueCharacters = ""

  for (c in plaintext.toCharArray())
    if (Character.isLetterOrDigit(c) && !uniqueCharacters.contains(c + ""))
      uniqueCharacters += c

  // Map each letter to a random letter in the alphabet (n.b. one-to-one)
  var alphabet = "abcdefghijklmnopqrstuvwxyz"
  for (c in uniqueCharacters.toCharArray()) {
    val x = alphabet[random.nextInt(alphabet.length)]
    cipher.put(c, x)
    alphabet = alphabet.replace(x + "", "")
  }

  val sb = StringBuilder()
  for (i in 0..plaintext.length - 1) {
    val c = plaintext[i]
    if (cipher.containsKey(c))
      sb.append(cipher[c])
    else
      sb.append(c)
  }

  return sb.toString()
}

private fun decrypt(ciphertext: String): String {
  val message = StringBuilder()

  for (c in ciphertext) {
    message.append(cipher.inverse().getIfAbsent(c, {' '}))
  }

  return message.toString()
}