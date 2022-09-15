package extras

import ai.hypergraph.kaliningraph.sampling.choose
import java.math.BigInteger
import java.math.BigInteger.*

// Finds triply-palindromic binary sphenic numbers, i.e. M = PQR where P, Q, R are
// distinct prime numbers and P, Q, R, PQ, QR, PR, PQR are all binary palindromes
// https://en.wikipedia.org/wiki/Sphenic_number
fun main() {
  var i = valueOf(100)
  val palprimes = mutableSetOf<BigInteger>()
  val triplePalprimes: MutableSet<Set<BigInteger>> = mutableSetOf()
  val checked = mutableSetOf<Set<BigInteger>>()

  while (true) {
    if (i.isNontrivialBinaryPalindrome() && i.isProbablePrime(1)) {
      i.toBinaryString().also { palprimes.add(i) }
      if (2 < palprimes.size) palprimes.choose(3).filter { it !in checked }.forEach { it ->
        checked.add(it)
        val (p, q, r) = it.toList().let { Triple(it[0], it[1], it[2]) }
        val candidate = setOf(p, q, r)
        if (isTripleBinaryPalindrome(p, q, r) && candidate !in triplePalprimes) {
          palprimes.removeIf { it < candidate.min() }
          triplePalprimes.add(setOf(p, q, r).also { println("$it = ${it.mult()}") })
        }
      }
    }
    i++
  }
}

fun isTripleBinaryPalindrome(p: BigInteger, q: BigInteger, r: BigInteger) =
  (p * q * r).isNontrivialBinaryPalindrome() &&
    (p * q).isNontrivialBinaryPalindrome() &&
    (p * r).isNontrivialBinaryPalindrome() &&
    (q * r).isNontrivialBinaryPalindrome()

fun BigInteger.isNontrivialBinaryPalindrome() =
  toBinaryString().isPalindrome() && isNonTrivial()

// Checks whether the solution is nontrivial, i.e., contains a mixture of 0s and 1s
private fun BigInteger.isNonTrivial() =
  toBinaryString().fold(0 to 0) { (a, b), it -> if (it == '0') a + 1 to b else a to b + 1 }
    .toList().minOrNull()!! > 0

fun String.isPalindrome() = this == reversed()

fun BigInteger.toBinaryString() = Integer.toBinaryString(toInt())