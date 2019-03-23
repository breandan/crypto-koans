package hashes

import java.math.BigInteger
import java.security.MessageDigest

fun main() {
  println(hash("the quick brown fox jumped over the lazy dog"))
}

private fun hash(input: String): BigInteger {
  val crypt = MessageDigest.getInstance("SHA-1")
  crypt.reset()
  crypt.update(input.toByteArray())

  return BigInteger(1, crypt.digest())
}