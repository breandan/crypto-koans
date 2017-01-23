package exercises

fun main(args: Array<String>) {
  val plaintext = "what do you call a camel with three humps"
  println("Plaintext:\t" + plaintext)

  val ciphertext = encrypt(plaintext)
  println("Ciphertext:\t" + ciphertext)

  val decrypted = decrypt(ciphertext)
  println("Decrypted:\t" + decrypted)

  if (decrypted == plaintext)
    println("Decryption succeeded!")
  else
    println("Decryption failure!")
}

/**
 * Exercise #1: Fix the following methods
 */

private fun decrypt(ciphertext: String): String {
  return ciphertext
    .replace("v", "e")
    .replace("x", "a")
    .replace("z", "l")
}

private fun encrypt(message: String): String {
  return message
    .replace("e", "v")
    .replace("a", "x")
    .replace("l", "z")
}
