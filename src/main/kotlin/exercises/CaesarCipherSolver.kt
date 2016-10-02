package exercises

import org.eclipse.collections.impl.bag.mutable.HashBag
import java.io.File
import java.util.*
import java.util.regex.Pattern

private val riddle = "jung qb lbh pnyy na nyyvtngbe jrnevat n irfg?"
private val answer = "vi diqznodbvojm!"

private val dictionary = HashBag<String>()

fun main(args: Array<String>) {
  val sc = Scanner(File("src/main/resources/google-10000-english.txt"))

  while (sc.hasNextLine()) {
    dictionary.add(sc.nextLine())
  }

  println(dictionary.size)

  // Exercise #2: Crack the riddle!
  // Your code goes here
}

private fun getShiftChar(c: Char, shift: Int): Char {
  if(!c.isLetter())
    return c

  val x = c.toInt() + shift
  val y = x - 97
  val z = Math.floorMod(y, 26)
  val i = (z + 97).toChar()
  return i
}

