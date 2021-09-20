package ciphers

import kotlin.random.Random

val random: Random = Random(1)

fun main() {
  val plaintext = "four score and seven years ago our fathers brought forth to this continent a new nation"

  // Make this set larger to increase the difficulty of cryptanalysis
  val codomain = (('A'..'Z').toSet() + ('0'..'9').toSet()).shuffled(random).toMutableList()

  val mostFrequentChars = plaintext.lowercase().filter { it.isLetter() }
    .groupingBy { it }.eachCount().entries.sortedByDescending { it.value }
    .associate { (k, v) -> k to v.toDouble() / plaintext.length.toDouble() }

  println("Plaintext histogram (size=${mostFrequentChars.size}): $mostFrequentChars")

  val key = HashMap<Char, MutableSet<Char>>()

  mostFrequentChars.forEach { (t, _) -> key[t] = mutableSetOf(codomain.removeAt(0)) }

  while (codomain.isNotEmpty())
    key[mostFrequentChars.maxByOrNull { it.value / key[it.key]!!.size }!!.key]!!.add(codomain.removeAt(0))

  println("Key" + key.entries)

  val ciphertext = encrypt(plaintext, key)

  // We want this histogram to be as flat as possible to inhibit frequency analysis
  val cipherHistogram = ciphertext.groupingBy { it }.eachCount().entries.sortedByDescending { it.value }
  println("Ciphertext histogram (size=${cipherHistogram.size}): $cipherHistogram")

  println("Ciphertext: $ciphertext")
  println("Decrypted: " + decrypt(ciphertext, key))
}

fun encrypt(plaintext: String, key: Map<Char, Set<Char>>) =
  plaintext.lowercase().map { key[it]?.random(random) ?: it }.joinToString("")

fun decrypt(
  ciphertext: String, key: Map<Char, Set<Char>>,
  revKey: Map<Char, Char> = key.entries.flatMap { (k, v) -> v.map { it to k } }.toMap()
) = ciphertext.map { revKey[it] ?: it }.joinToString("")