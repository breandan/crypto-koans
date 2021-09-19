package ciphers

import kotlin.random.Random

fun main() {
  val random = Random(1)

  val plaintext = "four score and seven years ago our fathers brought forth to this continent a new nation"

  val codomain = (('a'..'z').toSet() + ('0'..'9').toSet()).shuffled(random)

  val mostFrequentChars = plaintext.lowercase().filter { it.isLetter() }.groupingBy { it }
    .eachCount().entries.sortedByDescending { it.value }

  println("Plaintext histogram (size=${mostFrequentChars.size}): $mostFrequentChars")

  val pairs = codomain.mapIndexed { i, c -> mostFrequentChars[i % mostFrequentChars.size].key to c }

  val key = HashMap<Char, MutableSet<Char>>()
  pairs.forEach { (a, b) -> key.getOrPut(a) { mutableSetOf() }.add(b) }
  println("Key" + key.entries)

  val ciphertext = plaintext.lowercase().map { key[it]?.random(random) ?: it }.joinToString("")

  println("Ciphertext histogram: " + ciphertext.groupingBy { it }.eachCount().entries.sortedByDescending { it.value })

  println("Ciphertext: $ciphertext")
}