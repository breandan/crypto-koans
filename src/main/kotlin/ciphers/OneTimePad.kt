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
  val plaintext = message.toCharArray()

  // Generate secure random key (never reuse this)
  val pad = IntArray(PAD_SIZE)
  for (i in pad.indices) {
    pad[i] = secureRandom.nextInt(ALPHABET_SIZE)
  }

  // Combine the key with the plaintext
  val ciphertext = CharArray(PAD_SIZE)
  for (i in 0..PAD_SIZE - 1) {
    if (i < plaintext.size)
      ciphertext[i] = getShiftChar(plaintext[i], pad[i])
    else
      ciphertext[i] = getShiftChar(' ', pad[i])
  }

  println("Ciphertext: " + String(ciphertext))

  // Separate the key from the ciphertext
  val decrypted = CharArray(PAD_SIZE)
  for (i in 0..PAD_SIZE - 1) {
    decrypted[i] = getShiftChar(ciphertext[i], -pad[i])
  }

  println("Decrypted text: " + String(decrypted))
}

private fun getShiftChar(c: Char, shift: Int): Char {
  val x = c.toInt() + shift - 32
  val z = Math.floorMod(x, ALPHABET_SIZE)
  val i = (z + 32).toChar()
  return i
}
