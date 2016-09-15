import java.math.BigInteger

fun main(args: Array<String>) {
  display("In programming, we often treat numbers as characters. For example:")

  (33..126).forEachIndexed { i, it ->
    if ((i + 1) % 8 == 0) println()
    print("" + it.toInt() + "\t→\t" + it.toChar() + "\t|\t")
  }

  val c = display("\n\nIf you enter a string, I'll tell you the numbers: ")
  display(c.toByteArray().joinToString {
   it.toChar() + "→" + it.toString()
  })

  display("We can also treat strings of characters as large numbers.")

  val s = "This sentence can be encoded as the following number: "
  display(s)
  display(BigInteger(s.toByteArray()).toString())
}

fun display(s: String = ""): String {
  print(s)
  val c = readLine().orEmpty()
  if (c.contains(0.toChar()))
    System.exit(0)
  println()

  return c
}