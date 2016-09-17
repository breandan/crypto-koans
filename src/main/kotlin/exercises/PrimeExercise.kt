package exercises

private fun isPrime(number: Int): Boolean {
  // Your code goes here

  return true
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
}
