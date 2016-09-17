package exercises

import java.math.BigInteger
import java.util.*

private fun isPrime(number: Int): Boolean {
  // Your code goes here

  return true
}

private fun factor(number: Int): Collection<Int> {
  val primeFactors = ArrayList<Int>()

  // Your code goes here

  return primeFactors
}

fun main(args: Array<String>) {
  // Filter for all integers from 1 to 100 and keep if primes
  val primes = (1..100).filter { isPrime(it) }

  // Print out the primes we have found
  var index = 0
  for (i in primes) {
    // If index is divisible by 10, start a new line
    if (index.mod(10) == 0)
      println()

    print(i.toString())
    if (index < primes.size - 1)
      print(",\t")

    index++
  }

  println("Factoring large numbers...")
  var totalTime = BigInteger.ZERO
  for (i in Int.MAX_VALUE downTo Int.MAX_VALUE - 10000) {
    val startTime = System.nanoTime()
    val factors = factor(i)
    totalTime = totalTime.add(BigInteger.valueOf(System.nanoTime().minus(startTime)))
    if (factors.size.equals(2) || factors.size > 20)
      println(factors)
  }

  println("Average time: " + totalTime.divide(BigInteger.valueOf(10000)) + "ns")
}
