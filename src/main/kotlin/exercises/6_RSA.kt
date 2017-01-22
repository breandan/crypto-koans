package exercises

import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

private val BITS = 14
private val r = SecureRandom()
private val p = BigInteger.probablePrime(BITS, r)
private val q = BigInteger.probablePrime(BITS, r)
private val φ = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))

val pq = p.multiply(q)
var e = BigInteger.valueOf(3)

fun main(args: Array<String>) {
  while (φ.gcd(e) > BigInteger.ONE)
    e = e.add(BigInteger.valueOf(2))

  println("Public key: " + pq)
  val s = encrypt("yes")
  println("Ciphertext: " + s)

  val d = decrypt(s)
  println("Decrypted:  " + d)
}

private fun encrypt(plaintext: String): String {
  val m = plaintext.toByteArray()
  // c = M^e (mod pq)
  val c = BigInteger(m).modPow(e, pq)
  return Base64.getEncoder().encodeToString(c.toByteArray())
}

private fun decrypt(ciphertext: String): String {
  val c = Base64.getDecoder().decode(ciphertext)

  // ed = 1 (mod φ)
   val d = e.modInverse(φ)
//  val d = BigInteger.valueOf(modInverse(e.toLong(), φ.toLong()))

  println(d)
  // C^d (mod pq)
  val p = BigInteger(c).modPow(d, pq)
  return String(p.toByteArray())
}

/**
 * Returns a number x, s.t. ax = 1 (mod m)
 */

private fun modInverse(a: Long, m: Long): Long {
  var x = 1L
  var one = 1L

  // Your code goes here


  return x
}
