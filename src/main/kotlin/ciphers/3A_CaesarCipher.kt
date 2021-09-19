package ciphers

/**
 * The Caesar cipher shifts each letter n-letters down the alphabet.
 */

fun main() {
  val message = "meet me at secret location next wednesday afternoon"
  val secretKey = 6

  val ciphertext = encrypt(message, secretKey)
  println("Ciphertext: $ciphertext")
  bruteForce(ciphertext)
}

private fun bruteForce(ciphertext: String) {
  println("Brute forcing key... ")

  val dictionary = arrayOf("secret")
  for (i in 0 downTo -26 + 1) {
    val candidate = decrypt(ciphertext, i)
    println(candidate)

    for (s in dictionary) {
      if (candidate.contains(s)) {
        println("Plaintext found! Terminating...")
        return
      }
    }
  }
}

private fun decrypt(ciphertext: String, key: Int): String =
  encrypt(ciphertext, -key)

private fun encrypt(string: String, key: Int): String =
  string.map { getShiftChar(it, key) }.joinToString("")

private fun getShiftChar(c: Char, shift: Int): Char {
  if(!c.isLetter())
    return c

  val x = c.code + shift
  val y = x - 97
  val z = Math.floorMod(y, 26)
  val i = (z + 97).toChar()
  return i
}