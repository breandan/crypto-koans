package util

fun main(args: Array<String>) {
  val plaintext = "Hello world! こんにちは、世界!"
  println(plaintext)

  val binary = plaintext
    .map { Integer.toBinaryString(it.toInt()) }
    .joinToString(" ")
  println(binary)

  val d = binary.split(" ")
    .map { Integer.parseInt(it, 2).toChar() }
    .joinToString("")
  println(d)
}

/*
 * In modulus-2 addition: 0⊕0=0, 1⊕0=1, 1⊕1=0
 *
 *   10100100 10011010 10011000 10011010
 * ⊕ 10100100 10011010 10011000 10011010
 * -------------------------------------
 *   01010111 01101001 01101011 01101001
 */
