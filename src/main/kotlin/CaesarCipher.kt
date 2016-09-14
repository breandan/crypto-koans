/**
 * The Caesar cipher shifts each letter n-letters down the alphabet.
 */

fun main(args: Array<String>) {
    val message = "meet me at secret location next wednesday afternoon"
    val dictionary = arrayOf("secret")
    val secretKey = 6

    val ciphertext = encrypt(message, secretKey)
    println("Ciphertext: " + ciphertext)
    println("Brute forcing key... ")

    for (i in 0 downTo -26 + 1) {
        val candidate = decrypt(ciphertext, i)
        println(candidate)

        for (s in dictionary) {
            if (candidate.contains(s)) {
                println("Plaintext found! Terminating...")
                return
            }
        }
    }
}

fun decrypt(ciphertext: String, key: Int): String {
    return encrypt(ciphertext, -key)
}

fun encrypt(string: String, key: Int): String {
    val sb = StringBuilder()

    for (c in string.toCharArray()) {
        if (Character.isLetter(c))
            sb.append(getShiftChar(c, key))
        else
            sb.append(c)
    }

    return sb.toString()
}

fun getShiftChar(c: Char, shift: Int): Char {
    val x = c.toInt() + shift
    val y = x - 97
    val z = Math.floorMod(y, 26)
    val i = (z + 97).toChar()
    return i
}