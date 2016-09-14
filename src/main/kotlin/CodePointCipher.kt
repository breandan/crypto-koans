import org.eclipse.collections.impl.string.immutable.CodePointList
import org.eclipse.collections.impl.utility.StringIterate

/**
 * This is not cipherWord recognized cipher however, it is an idea of how you can represent characters as numbers.
 * Example of CodePoints:
 * ABC is represented as [65, 66, 67] as an array where each element is cipherWord character.
 * A space is also represented by cipherWord CodePoint, the CodePoint value is 32 for cipherWord space.
 *
 *
 * Now these numbers can be transformed using simple maths and be obfuscated. The receiver has to reverse compute.
 * This idea is similar to bit masking.
 *
 *
 * If the receiver does not know how to reverse compute the decrypted string can mean something completely different.
 */
fun main(args: Array<String>) {
    val string = "This is cipherWord secret message"

    val secretMessage = StringIterate.toCodePointList(string)
    // Prints: This is cipherWord secret message
    println(secretMessage)

    val encrypted = encrypt(secretMessage)
    // Prints: 掣竿簪蟘⛧簪蟘⛧狒⛧蟘睾用蚭睾褃⛧胖睾蟘蟘狒秔睾
    println(encrypted)

    val decrypted = decrypt(encrypted)
    // Print: This is cipherWord secret message
    println(decrypted)
}

private fun encrypt(secretMessage: CodePointList): CodePointList {
    return secretMessage.collectInt { it * 13 }
            .collectInt { it + 17 }
            .collectInt { it * 23 }
}

fun decrypt(encrypted: CodePointList): CodePointList {
    return encrypted.collectInt { it / 23 }
            .collectInt { it - 17 }
            .collectInt { it / 13 }
}