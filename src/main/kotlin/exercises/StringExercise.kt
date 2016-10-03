package exercises

import java.awt.Toolkit
import java.math.BigInteger

fun main(args: Array<String>) {
  prompt("In programming, we often treat characters as numbers. For example:")

  ('!'..'~').forEachIndexed { i, it ->
    if ((i + 1) % 8 == 0) println()
    print("" + it + "\t→\t" + it.toInt() + "\t|\t")
  }

  val c = prompt("\n\nIf you type some characters, I'll tell you the numbers: ")
  c.trim().toByteArray().forEach {
    print("'" + it.toChar() + "'→" + it + ", ")
  }

  prompt("\n\nWe can also treat strings of characters as (large) numbers.")

  var s = prompt("What's your name? ")
  if (s.isEmpty()) {
    s = "Thomas"
    print("Let's say your name is '$s'. ")
  }

  val integer = BigInteger(s.toByteArray())

  prompt("'$s' can be encoded as a decimal number: " + integer.toString())

  prompt("We can represent the same number in binary: " + integer.toString(2))

  prompt("Or in octal (base-8): " + integer.toString(8))

  prompt("Or in hexadecimal (base-16): " + integer.toString(16))

  println("Or as a bitmap image:\n")
  val bin = integer.toString(2)

  bin.forEachIndexed { i, it ->
    val sqrt = Math.ceil(Math.sqrt(bin.length.toDouble()))
    if (i <= bin.length) {
      if (it == '0') print("   ") else print(" █ ")
      if ((i + 1).mod(sqrt.toInt()) == 0)
        println()
    }
  }

  println("\n\nOr as a sound: \n")
  for (it in bin)
    if (it == '0')
      Thread.sleep(200)
    else
      Toolkit.getDefaultToolkit().beep()

  prompt("It all depends how you choose to represent (and interpret) the data.")
}

fun prompt(s: String = ""): String {
  print(s)
  val c = readLine().orEmpty()
  if (c.contains(0.toChar()))
    System.exit(0)
  println()

  return c
}