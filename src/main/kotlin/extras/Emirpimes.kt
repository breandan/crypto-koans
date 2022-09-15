package extras

import ai.hypergraph.kaliningraph.sampling.choose
import java.math.BigInteger
import java.math.BigInteger.*

// Finds triply-palindromic binary sphenic numbers, i.e. M = PQR where P, Q, R are
// distinct prime numbers and P, Q, R, PQ, QR, PR, PQR are all binary palindromes
// https://en.wikipedia.org/wiki/Sphenic_number
fun main() {
  var i = valueOf(9100)
  val emirps = mutableSetOf<BigInteger>(
    valueOf(7523L),
    valueOf(7547L),
    valueOf(7561L),
    valueOf(7577L),
    valueOf(7589L),
    valueOf(7681L),
    valueOf(7757L),
    valueOf(7841L),
    valueOf(9001L),
    valueOf(9013L),
    valueOf(9103L),
    valueOf(9133L),
    valueOf(9173L)
  )
  val bestCandidates: MutableSet<Set<BigInteger>> = mutableSetOf(setOf(valueOf(9133L), valueOf(9103L)))
  val checked = mutableSetOf<Set<BigInteger>>()
  while (true) {
    if (i.isEmirp() && i.isProbablePrime(1)) {
      i.toBinaryString().also { emirps.add(i.also { println(i) }) }
      if (emirps.size > 2) emirps.choose(2).filter { it !in checked && bestCandidates.last().mult() < it.mult() }
        .forEach { pair: Set<BigInteger> ->
          checked.add(pair.toSet())
          val (p, q) = pair.toList().let { Pair(it[0], it[1]) }
          if ((p * q).isEmirpimes())
            bestCandidates.add(setOf(p, q).also { println(it.joinToString(" * ", "", " = ") + it.mult()) })
        }
    }
    i++
  }
}

fun BigInteger.isEmirp() =
  BigInteger(toBinaryString().reversed(), 2).isProbablePrime(1) &&
  toBinaryString() != toBinaryString().reversed() &&
  BigInteger(toString()).isProbablePrime(1) &&
  BigInteger(toString().reversed()).isProbablePrime(1) &&
  toString() != toString().reversed()

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