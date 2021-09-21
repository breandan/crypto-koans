package extras

import java.math.BigInteger
import java.math.BigInteger.*

// Finds triply-palindromic binary sphenic numbers, i.e. M = PQR where P, Q, R are
// distinct prime numbers and P, Q, R, PQ, QR, PR, PQR are all binary palindromes
// https://en.wikipedia.org/wiki/Sphenic_number
fun main() {
  var i = valueOf(100)
  val palindromicPrimes = mutableSetOf<BigInteger>()
  val triplePalindromicPrimes = mutableSetOf<Set<BigInteger>>()
  while (true) {
    if (i.isNontrivialBinaryPalindrome() && i.isProbablePrime(1)) palindromicPrimes.add(i)
    var j = 0
    do {
      if (palindromicPrimes.size < 3) break
      // This is a very inefficient algorithm, but it seems to get the job done...
      val (p, q) = palindromicPrimes.shuffled().take(2).let { Pair(it[0], it[1]) }
      val candidate = setOf(p, q)
      if (
        isDoubleBinaryPalindrome(p, q) &&
        p.isNontrivialBinaryPalindrome() &&
        q.isNontrivialBinaryPalindrome() &&
        candidate !in triplePalindromicPrimes
      ) { println("$p $q"); triplePalindromicPrimes.add(setOf(p, q)) }
      j++
    } while(j < 1000)
    i++
  }
}

fun isDoubleBinaryPalindrome(p: BigInteger, q: BigInteger) =
  (p * q).isNontrivialBinaryPalindrome() &&
    p.isNontrivialBinaryPalindrome() &&
    q.isNontrivialBinaryPalindrome()

fun isTripleBinaryPalindrome(p: BigInteger, q: BigInteger, r: BigInteger) =
  (p * q * r).isNontrivialBinaryPalindrome() &&
    (p * q).isNontrivialBinaryPalindrome() &&
    (p * r).isNontrivialBinaryPalindrome() &&
    (q * r).isNontrivialBinaryPalindrome()

fun BigInteger.isNontrivialBinaryPalindrome() =
  toBinaryString().isPalindrome() && isNonTrivial()

// Checks whether the solution is nontrivial, i.e., contains a mixture of 0s and 1s
fun BigInteger.isNonTrivial() =
  toBinaryString().fold(0 to 0) { (a, b), it -> if (it == '0') a + 1 to b else a to b + 1 }.toList().minOrNull()!! > 0

fun String.isPalindrome() = this == reversed()

fun BigInteger.toBinaryString() = Integer.toBinaryString(toInt())