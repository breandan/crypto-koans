package ciphers

import java.security.SecureRandom
import java.util.*

/**
 * The random cipher, or mixed alphabet cipher, maps each letter in the
 * alphabet with a randomly chosen letter of the same alphabet, without
 * replacement (ie. A->M, B->M is not allowed). This cipher is also commonly
 * referred to as a "monoalphabetic substitution cipher".
 */

private val cipher = LinkedHashSet<Char>()

fun main() {
  val random = SecureRandom()
  cipher.addAll(('a'..'z').foldIndexed(arrayListOf()) { idx, list, c ->
    list.add(random.nextInt(idx + 1), c); list
  })
  val message = "four score and seven years ago our fathers".toLowerCase()

  println("Plaintext:  $message")
  val ciphertext = encrypt(message)
  println("Ciphertext: $ciphertext")
  println("Decrypted:  " + decrypt(ciphertext))
}


private fun encrypt(plaintext: String): String =
  plaintext.map { getShiftChar(it, 1) }.joinToString("")

private fun decrypt(ciphertext: String): String =
  ciphertext.map { getShiftChar(it, -1) }.joinToString("")

private fun getShiftChar(c: Char, i: Int): Char {
  return cipher.elementAt(Math.floorMod(cipher.indexOf(c) + i, cipher.size))
}