package asymmetric

import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

private val r = SecureRandom()
private val p = BigInteger.probablePrime(1024, r)
private val q = BigInteger.probablePrime(1024, r)
private val pq = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))

public val n = p.multiply(q)
public var e = BigInteger.valueOf(3)

fun main(args: Array<String>) {
  while (pq.gcd(e).compareTo(BigInteger.ONE) > 0)
    e = e.add(BigInteger.valueOf(2))

  val s = encrypt("Hello world")
  println(s)

  val d = decrypt(s)
  println(d)
}

fun encrypt(plaintext: String): String {
  return String(
    Base64
      .getEncoder()
      .encode(
        BigInteger(plaintext.toByteArray())
          .modPow(e, n)
          .toByteArray()))
}

fun decrypt(ciphertext: String): String {
  return String(
    BigInteger(
      Base64
        .getDecoder()
        .decode(ciphertext))
      .modPow(e.modInverse(pq), n)
      .toByteArray())
}
