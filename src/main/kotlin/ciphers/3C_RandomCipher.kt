package ciphers

import java.security.SecureRandom
import java.util.*

/**
 * The random cipher, or mixed alphabet cipher, maps each letter in the
 * alphabet with a randomly chosen letter of the same alphabet, without
 * replacement (ie. A->M, B->M is not allowed). This cipher is also commonly
 * referred to as a "monoalphabetic substitution cipher".
 */

fun main() {
  val random = SecureRandom()
  val alphabet = ('a'..'z')
  val key = alphabet.zip(alphabet.shuffled(random)).toMap()
  val message = "four score and seven years ago our fathers brought forth to this continent a new nation".lowercase()

  println("Plaintext:  $message")
  val ciphertext = encrypt(message, key)
  println("Ciphertext: $ciphertext")
  println("Decrypted:  " + decrypt(ciphertext, key))
}

private fun encrypt(plaintext: String, key: Map<Char, Char>): String =
  plaintext.map { key[it] ?: it }.joinToString("")

private fun decrypt(ciphertext: String, key: Map<Char, Char>): String =
  ciphertext.map { v -> key.entries.firstOrNull { it.value == v }?.key ?: v }.joinToString("")