package asymmetric

import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

private val r = SecureRandom()
private val p = BigInteger.probablePrime(1024, r)
private val q = BigInteger.probablePrime(1024, r)
private val φ = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))

public val pq = p.multiply(q)
public var e = BigInteger.valueOf(3)

fun main(args: Array<String>) {
  while (φ.gcd(e).compareTo(BigInteger.ONE) > 0)
    e = e.add(BigInteger.valueOf(2))

  println("Public key: " + pq)
  val s = encrypt("this is a very difficult message to brute force")
  println("Ciphertext: " + s)

  val d = decrypt(s)
  println("Decrypted:  " + d)
}

internal fun encrypt(plaintext: String): String {
  return String(
    Base64
      .getEncoder()
      .encode(
        BigInteger(plaintext.toByteArray())
          .modPow(e, pq)
          .toByteArray()))
}

internal fun decrypt(ciphertext: String): String {
  return String(
    BigInteger(
      Base64
        .getDecoder()
        .decode(ciphertext))
      .modPow(e.modInverse(φ), pq)
      .toByteArray())
}