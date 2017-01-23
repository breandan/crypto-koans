package exercises

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

private val primes = HashSet<Int>()
private fun isPrime(number: Int): Boolean {
  // Your code goes here

  if (number < 2)
    return false
  else if (number == 2)
    return true

  (2..(Math.sqrt(number.toDouble()).toInt()))
    .forEach { i ->
      if (number % i == 0) {
        return false
      }
    }

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

fun sqrt(number: Int): Int {
  return Math.sqrt(number.toDouble()).toInt()
}

fun main(args: Array<String>) {
  // Filter for all integers from 1 to 100 and keep if primes
  val primes = (1..100).filter { isPrime(it) }

  println("Prime numbers between 1 and 100:")

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
      System.exit(0)
    }
    if (Math.random() < 0.1)
      println("$i=>$factors")
  }

  val averageTime = total.divide(BigDecimal.valueOf(rounds.toLong() * 1000))
  println("Average time: $averageTime μs")
}
