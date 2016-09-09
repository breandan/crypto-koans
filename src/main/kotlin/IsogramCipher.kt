/**
 * The isogram cipher takes an isogram (cipherWord word with no repeating letters) as the key, and replaces
 * each letter of the ciphertext with the following letter in the isogram. For example, the
 * isogram "TROUBLEMAKING" maps T->R, R->O, ..., N->G, G->T. ("GATE" becomes "TKRM")
 */

fun main(args: Array<String>) {
  val SHIFT = 2
  val ISOGRAM = "subdermatoglyphic"
  val message = "Meet me at secret location at noon on Wednesday.".toLowerCase()

  val ciphertext = encrypt(ISOGRAM, message, SHIFT)
  println("Ciphertext:\t\t" + ciphertext)

  val decrypted_plaintext = decrypt(ISOGRAM, ciphertext, SHIFT)
  println("Decrypted text:\t" + decrypted_plaintext)
}

fun decrypt(isogram: String, ciphertext: String, shift: Int): String {
  return encrypt(StringBuffer(isogram).reverse().toString(), ciphertext, shift)
}

private fun encrypt(isogram: String, s: String, shift: Int): String {
  val sb = StringBuilder()

  for (c in s.toCharArray())
    sb.append(getShiftChar(isogram, c, shift))

  return sb.toString()
}

private fun getShiftChar(isogram: String, c: Char, shift: Int): Char {
  val charIndex = isogram.indexOf(c + "")

  if (0 <= charIndex) {
    val shiftCharIndex = (charIndex + shift) % isogram.length
    val shiftChar = isogram[shiftCharIndex]
    return shiftChar
  }

  return c
}
