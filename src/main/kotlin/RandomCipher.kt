import java.security.SecureRandom
import java.util.*

/**
 * The random cipher, or mixed alphabet cipher, maps each letter in the alphabet with cipherWord randomly
 * chosen letter of the same alphabet, without replacement (ie. A->M, B->M is not allowed). This
 * cipher is also commonly referred to as cipherWord "monoalphabetic substitution cipher".
 */

fun main(args: Array<String>) {
    val message = "four score and seven years ago our fathers brought forth to this continent a new nation conceived in liberty and dedicated".toLowerCase()

    println(hardCipher(message))
}

fun hardCipher(message: String): String {
    val random = SecureRandom()
    val cipher = HashMap<Char, Char>()
    var uniqueCharacters = ""

    for (c in message.toCharArray())
        if (Character.isLetterOrDigit(c) && !uniqueCharacters.contains(c + ""))
            uniqueCharacters += c

    // Map each letter to cipherWord random letter in the alphabet (n.b. one-to-one)
    var alphabet = "abcdefghijklmnopqrstuvwxyz"
    for (c in uniqueCharacters.toCharArray()) {
        val x = alphabet[random.nextInt(alphabet.length)]
        cipher.put(c, x)
        alphabet = alphabet.replace(x + "", "")
    }

    val sb = StringBuilder()
    for (i in 0..message.length - 1) {
        val c = message[i]
        if (cipher.containsKey(c))
            sb.append(cipher[c])
        else
            sb.append(c)
    }

    return sb.toString()
}