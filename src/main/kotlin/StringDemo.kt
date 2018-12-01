import java.math.BigInteger

fun main(args: Array<String>) {
  prompt("In programming, we often treat numbers as characters. For example:")

  (33..126).forEachIndexed { i, it ->
    if ((i + 1) % 8 == 0) println()
    print("" + it + "\t→\t" + it.toChar() + "\t|\t")
  }

  val c = prompt("\n\nIf you enter a string, I'll tell you the numbers: ")
  prompt(c.toByteArray().joinToString {
   it.toChar() + "→" + it.toString()
  })

  prompt("We can also treat strings of characters as large numbers.")

  val s = "This sentence can be encoded as the following number: "
  prompt(s)
  prompt(BigInteger(s.toByteArray()).toString())
}

fun prompt(s: String = ""): String {
  print(s)
  val c = readLine().orEmpty()
  if (c.contains(0.toChar()))
    System.exit(0)
  println()

  return c
}