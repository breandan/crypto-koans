package exercises

import java.awt.Toolkit
import java.math.BigInteger

fun main(args: Array<String>) {
  prompt("Press [ENTER] to continue...")
  prompt("In this exercise, we'll learn to represent text in different ways.")
  prompt("In programming, we often treat characters as numbers. For example:")

  ('!'..'~').forEachIndexed { i, it ->
    if ((i + 1) % 8 == 0) println()
    print("" + it + "\t→\t" + it.toInt() + "\t|\t")
  }
  println()

  var s = prompt("\n\nIf you type your name below, I'll convert it:\n\n")
  if (s.isEmpty()) {
    s = "Thomas"
    println("Let's say your name is '$s'.\n")
  }

  println(" ________________________________ ")
  println("| Character | Decimal | Binary   |")
  println("|-----------|---------|----------|")
  s.trim().toByteArray().forEach {
    val ci = byte(it)
    val char = it.toChar().toString().padStart(6).padEnd(11)
    val decimal = it.toString().padStart(6).padEnd(9)
    val binary = ci.toString(2).padStart(8,'0').padStart(9).padEnd(10)
    print("|" + char + "|" + decimal)
    print("|" + binary + "|\n")
  }

  println(" ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾ ")
  prompt("\nWe can also treat strings of characters as (large) numbers.")

  val asInt = BigInteger(s.toByteArray())
  val bin = asInt.toByteArray().map { byte(it).toString(2).padStart(8, '0') }
    .joinToString(" ")


  prompt("We can represent '$s' in binary: " + bin.padStart(8, '0'))

  prompt("'$s' can be encoded as a decimal number: " + asInt.toString())

  prompt("Or in octal (base-8): " + asInt.toString(8))

  prompt("Or in hexadecimal (base-16): " + asInt.toString(16))

  println("Or as a bitmap image:\n")
  val bit = asInt.toString(2)

  bit.forEachIndexed { i, it ->
    val sqrt = Math.ceil(Math.sqrt(bit.length.toDouble()))
    if (i <= bit.length) {
      if (it == '0') print("   ") else print(" █ ")
      if ((i + 1).mod(sqrt.toInt()) == 0)
        println()
    }
  }

  prompt("\n\n\nOr as a sound:")
  for (it in bit)
    if (it == '0') {
      Thread.sleep(100)
      print(' ')
    }
    else {
      Toolkit.getDefaultToolkit().beep()
      print('♪')
    }

  println("\n\n")
  println("It all depends how you choose to represent (and interpret) the " +
    "data.")
}

fun byte(vararg bytes: Byte) : BigInteger {
  return BigInteger(bytes)
}

fun prompt(s: String = ""): String {
  println(s)
  val c = readLine().orEmpty()
  if (c.contains(0.toChar()))
    System.exit(0)
  println()

  return c
}