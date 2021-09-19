package ciphers

import java.io.File

/**
 * The isogram cipher takes an isogram (a word with no repeating letters) as
 * the key, and replaces each letter of the ciphertext with the following
 * letter in the isogram. For example, the isogram "TROUBLEMAKING" maps T->R,
 * R->O, ..., N->G, G->T. ("GATE" becomes "TKRM").
 */

fun main() {
  val SHIFT = 1
  val ISOGRAM = "subdermatoglyphic"
  val message = "Meet me at secret location at noon on Wednesday".lowercase()

  val ciphertext = encrypt(ISOGRAM, message, SHIFT)
  println("Ciphertext:\t\t$ciphertext")

  val decrypted_plaintext = decrypt(ISOGRAM, ciphertext, SHIFT)
  println("Decrypted text:\t$decrypted_plaintext")

  File("src/main/resources/ciphertext").writeText(ciphertext)
}

private fun decrypt(isogram: String, ciphertext: String, shift: Int): String =
  encrypt(isogram.reversed(), ciphertext, shift)

private fun encrypt(isogram: String, s: String, shift: Int): String {
  val sb = StringBuilder()

  for (c in s.toCharArray())
    sb.append(getShiftChar(isogram, c, shift))

  return sb.toString()
}

private fun getShiftChar(isogram: String, c: Char, shift: Int): Char {
  val charIndex = isogram.indexOf(c + "")

  if (0 <= charIndex) {
    return isogram[(charIndex + shift) % isogram.length]
  }

  return c
}