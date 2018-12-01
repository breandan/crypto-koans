package ciphers

import org.eclipse.collections.impl.string.immutable.CodePointList
import org.eclipse.collections.impl.utility.StringIterate

/**
 * This is not a recognized cipher however, it is an idea of how you can represent characters as numbers.
 * Example of CodePoints:
 * ABC is represented as [65, 66, 67] as an array where each element is a character.
 * A space is also represented by a CodePoint, the CodePoint value is 32 for a space.
 * <p>
 * Now these numbers can be transformed using simple maths and be obfuscated. The receiver has to reverse compute.
 * This idea is similar to bit masking.
 * <p>
 * If the receiver does not know how to reverse compute the decrypted string can mean something completely different.
 */

fun main(args: Array<String>) {
  val string = "This is a secret message"

  val secretMessage = StringIterate.toCodePointList(string)
  // Prints: This is a secret message
  println("Plaintext:  $secretMessage")

  val encrypted = encrypt(secretMessage)
  // Prints: 掣竿簪蟘⛧簪蟘⛧狒⛧蟘睾用蚭睾褃⛧胖睾蟘蟘狒秔睾
  println("Ciphertext: $encrypted")

  val decrypted = decrypt(encrypted)
  // Print: This is a secret message
  println("Decrypted:  $decrypted")
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