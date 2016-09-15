package ciphers

import java.security.SecureRandom

/**
 * A one-time pad mixes the ciphertext with a long random key. Never use this
 * key twice!
 */

val secureRandom = SecureRandom()
val ALPHABET_SIZE = 94 //ASCII 32 - 126
val PAD_SIZE = 100

fun main(args: Array<String>) {
  val message = "Meet me at secret location at noon on Wednesday."

  // Generate secure random key (never reuse this)
  val pad = ByteArray(PAD_SIZE)
  secureRandom.nextBytes(pad)
  println("One time pad: " + pad.map { getShiftChar(0.toChar(), it) }
    .joinToString(""))

  val ciphertext = encrypt(message, pad)
  println("Ciphertext:   " + ciphertext)

  val decrypted = decrypt(ciphertext, pad)
  println("Decrypted:    " + decrypted)
  println(Byte.MAX_VALUE.toChar())
}

private fun encrypt(plaintext: String, pad: ByteArray): String {
  // Combine the key with the plaintext
  val ciphertext = CharArray(pad.size)
  for (i in 0..pad.size - 1) {
    if (i < plaintext.length)
      ciphertext[i] = getShiftChar(plaintext[i], pad[i])
    else
      ciphertext[i] = getShiftChar(' ', pad[i])
  }
  return String(ciphertext)
}

private fun decrypt(ciphertext: String, pad: ByteArray): String {
  // Separate the key from the ciphertext
  val decrypted = CharArray(pad.size)
  for (i in 0..pad.size - 1) {
    decrypted[i] = getShiftChar(ciphertext[i], (-pad[i]).toByte())
  }
  return String(decrypted)
}

private fun getShiftChar(c: Char, shift: Byte): Char {
  val x = c.toInt() - 32 + shift
  val z = Math.floorMod(x, ALPHABET_SIZE)
  val i = (z + 32).toChar()
  return i
}