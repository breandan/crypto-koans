package extras

import java.math.BigInteger
import java.math.BigInteger.*

// Finds twin emirps whose product is an emirpimes
// https://en.wikipedia.org/wiki/Emirp
fun main() {
  (12000..999999).toList().shuffled().parallelStream().forEach { v ->
    val i = BigInteger("$v")
    if (i.isTwinEmirp()) {
      val (p, q) = i to BigInteger(i.toString().reversed())
      println(setOf(p, q).also { println(it.joinToString(" * ", "", " = ") + it.mult()) })
    }
  }
}

fun BigInteger.isTwinEmirp() =
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
    toBinaryString() != toBinaryString().reversed() &&
    toString() != toString().reversed()

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