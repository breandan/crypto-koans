package extras

import ai.hypergraph.kaliningraph.sampling.choose
import java.math.BigInteger
import java.math.BigInteger.*

// Finds triply-palindromic binary sphenic numbers, i.e. M = PQR where P, Q, R are
// distinct prime numbers and P, Q, R, PQ, QR, PR, PQR are all binary palindromes
// https://en.wikipedia.org/wiki/Sphenic_number
/*
9883
10039
10069
10079
10151
10159
10247
10247 * 10151 = 104017297
10321
10321 * 10159 = 104851039
10333
10429
10453
10613
10613 * 10429 = 110682977
10639
10639 * 10613 = 112911707
10711
 */
fun main() {
  var i = valueOf(2)
  while (true) {
    if (i.isEmirp()) {
      val (p, q) = BigInteger(i.toString()) to BigInteger(i.toString().reversed())
      println(setOf(p, q).also { println(it.joinToString(" * ", "", " = ") + it.mult()) })
    }
    i++
  }
}

fun BigInteger.isEmirp() =
  BigInteger(toBinaryString().reversed(), 2).isProbablePrime(1) &&
  toBinaryString() != toBinaryString().reversed() &&
  isProbablePrime(1) &&
  BigInteger(toString().reversed()).isProbablePrime(1) &&
  toString() != toString().reversed() &&
  (this * BigInteger(toString().reversed())).isEmirpimes() &&
  (this * BigInteger(toBinaryString().reversed(), 2)).isEmirpimes()

fun BigInteger.isEmirpimes() =
  isSemiprime(this) &&
    isSemiprime(BigInteger(toString().reversed())) &&
    isSemiprime(BigInteger(toBinaryString().reversed(), 2)) &&
    toBinaryString() != toBinaryString().reversed()

fun Set<BigInteger>.mult() = fold(ONE) { acc, bigInteger ->  acc * bigInteger }

fun primeDecomp(a: BigInteger): List<BigInteger>? {
  //impossible for values lower than 2
  var a = a
  if (a < TWO) { return null }

  val result: MutableList<BigInteger> = ArrayList()
  while (a.and(ONE) == ZERO) {
    a = a.shiftRight(1)
    result.add(TWO)
  }

  if (a != ONE) {
    var b = valueOf(3)
    while (b < a) {
      if (b.isProbablePrime(10)) {
        val dr = a.divideAndRemainder(b)
        if (dr[1] == ZERO) {
          result.add(b)
          a = dr[0]
        }
      }
      b = b.add(TWO)
    }
    result.add(b)
  }
  return result
}

fun isSemiprime(x: BigInteger): Boolean {
  val decomp = primeDecomp(x)
  return decomp != null && decomp.size == 2
}