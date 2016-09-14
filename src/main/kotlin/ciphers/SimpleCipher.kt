package ciphers

fun main(args: Array<String>) {
  val plaintext = "what do you call a camel with three humps"
  println("Plaintext:\t" + plaintext)

  val ciphertext = encrypt(plaintext)
  println("Ciphertext:\t" + ciphertext)

  val decrypted = decrypt(ciphertext)
  println("Decrypted:\t" + decrypted)

  if (decrypted.equals(plaintext))
    println("Decryption succeeded!")
  else
    println("Decryption failure!")
}

/**
 * Exercise #1: Fix the following methods
 */

private fun decrypt(ciphertext: String): String {
  return ciphertext
    .replace("p", "e")
    .replace("r", "a")
    .replace("z", "l")
}

private fun encrypt(message: String): String {
  return message
    .replace("e", "p")
    .replace("a", "r")
    .replace("l", "z")
}
