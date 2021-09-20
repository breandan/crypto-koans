package exercises

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.math.sqrt
import kotlin.system.exitProcess

private val primes = HashSet<Int>()
private fun isPrime(number: Int): Boolean {
  // Your code goes here
  return true
}

private fun factor(number: Int): Collection<Int> {
  val primeFactors = LinkedList<Int>()
  primeFactors.push(number)
  while (!isPrime(primeFactors.first)) {
    val composite = primeFactors.remove()
    val quotient = (2..sqrt(composite)).find { composite % it == 0 }!!
    primeFactors.push(quotient)
    primeFactors.push(composite / quotient)
  }

  return primeFactors
}

fun sqrt(number: Int): Int = sqrt(number.toDouble()).toInt()

fun main() {
  // Filter for all integers from 1 to 100 and keep if primes
  val primes = (1..100).filter { isPrime(it) }

  println("Prime numbers between 1 and 100:")

  // Print out the primes we have found
  for ((index, i) in primes.withIndex()) {
    // If index is divisible by 10, start a new line
    if (index.rem(10) == 0)
      println()

    print(i.toString())
    if (index < primes.size - 1)
      print(",\t")

  }

  println("\n\nFactoring large numbers...")
  var total = BigDecimal.ZERO
  val rounds = 100
  for (i in Int.MAX_VALUE downTo Int.MAX_VALUE - rounds) {
    val startTime = BigDecimal.valueOf(System.nanoTime())
    val factors = factor(i)
    total = total.add(BigDecimal.valueOf(System.nanoTime()).minus(startTime))

    val firstFactor = factors.first().toLong()
    if (!BigInteger.valueOf(firstFactor).isProbablePrime(100)) {
      println("You said $firstFactor is prime, but $firstFactor is not prime.")
      println("Please try again! Terminating...")
      exitProcess(0)
    }
    if (Math.random() < 0.1)
      println("$i=>$factors")
  }

  val averageTime = total.divide(BigDecimal.valueOf(rounds.toLong() * 1000))
  println("Average time: $averageTime μs")
}
