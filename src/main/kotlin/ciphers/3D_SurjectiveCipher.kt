package ciphers

import kotlin.random.Random

fun main() {
  val random: Random = Random(1)

  val plaintext = "four score and seven years ago our fathers brought forth to this continent a new nation"

  // Make this set larger to increase the difficulty of cryptanalysis
  val codomain = (('A'..'Z').toSet() + ('0'..'9').toSet()).shuffled(random).toMutableList()

  val mostFrequentChars = plaintext.lowercase().filter { it.isLetter() }.groupingBy { it }
    .eachCount().entries.sortedByDescending { it.value }.map { (k, v) -> k to v.toDouble() / plaintext.length.toDouble() }.toMap()

  println("Plaintext histogram (size=${mostFrequentChars.size}): $mostFrequentChars")

  val key = HashMap<Char, MutableSet<Char>>()

  mostFrequentChars.forEach { (t, _) -> key[t] = mutableSetOf(codomain.removeAt(0)) }

  while(codomain.isNotEmpty())
    key[mostFrequentChars.maxByOrNull { it.value / key[it.key]!!.size }!!.key]!!.add(codomain.removeAt(0))

  println("Key" + key.entries)

  val ciphertext = plaintext.lowercase().map { key[it]?.random(random) ?: it }.joinToString("")

  // We want this histogram to be as flat as possible to inhibit frequency analysis
  println("Ciphertext histogram: " + ciphertext.groupingBy { it }.eachCount().entries.sortedByDescending { it.value })

  println("Ciphertext: $ciphertext")
}