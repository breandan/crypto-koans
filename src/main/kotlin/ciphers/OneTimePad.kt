package ciphers

import java.security.SecureRandom

/**
 * An OTP mixes the ciphertext with a long random key. Never use this key twice!
 */

private val secureRandom = SecureRandom()
private val ALPHABET_SIZE = 94 //ASCII 32 - 126
private val PAD_SIZE = 100

fun main(args: Array<String>) {
  val message = "Meet me at secret location at noon on Wednesday."
  println("Plaintext:    " + message)

  // Generate secure random key (never reuse this)
  val pad = ByteArray(PAD_SIZE)
  secureRandom.nextBytes(pad)
  println("One time pad: " + pad.map { getShiftChar(0.toChar(), it) } .joinToString(""))

  val ciphertext = encrypt(message, pad)
  println("Ciphertext:   " + ciphertext)

  val decrypted = decrypt(ciphertext, pad)
  println("Decrypted:    " + decrypted)
  println(Byte.MAX_VALUE.toChar())
}

private fun encrypt(plaintext: String, pad: ByteArray): String {
  // Combine the key with the plaintext
  return plaintext.mapIndexed { i, c -> getShiftChar(c, pad[i]) }.joinToString("")
}

private fun decrypt(ciphertext: String, pad: ByteArray): String {
  // Separate the key from the ciphertext
  return ciphertext.mapIndexed { i, c -> getShiftChar(c, (-pad[i]).toByte()) }.joinToString("")
}

private fun getShiftChar(c: Char, shift: Byte): Char {
  val x = c.toInt() - 32 + shift
  val z = Math.floorMod(x, ALPHABET_SIZE)
  val i = (z + 32).toChar()
  return i
}