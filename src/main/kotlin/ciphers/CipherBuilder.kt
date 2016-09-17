package ciphers

import org.eclipse.collections.impl.bimap.mutable.HashBiMap
import exercises.prompt

private val cipher = HashBiMap<Char, Char>()

fun main(args: Array<String>) {
  var alphabet = "etaoinshrdlcumwfgypbvkjxqz"
  do {
    val message = prompt("Enter a new message: ")

    do {
      println("Encrypted message:   " + encrypt(message))
      println("Cipher key: " + cipher)
      println()
      println("Unencrypted letters: " +
        message.filter { !cipher.containsKey(it) }
          .toCharArray()
          .distinct()
          .joinToString(""))
      println("Unassigned alphabet:  " + alphabet)
      val reps = prompt("Enter replacement: ")

      var a = reps.find { message.contains(it) && it.isLetter() }
      while (a != null) {
        val b = reps.replace(a.toString(), "").find {
          it.isLetter()
        }

        if (b != null) {
          if(cipher.inverse().containsKey(b))
            cipher.remove(a)
          cipher.put(a, b)
          alphabet = alphabet.replace(b.toString(), "")
        }

        val collisions = cipher.valuesView().filter { message.contains(it) &&
          !cipher.containsKey(it) }
        if(collisions.isNotEmpty()) {
          println("To assign: " + collisions)
          println("(Decryption will fail unless you reassign these letters!)")
        }

        val c = reps.replace(a.toString(), "").replace(b.toString(), "")
        a = c.find { message.contains(it) && !cipher.containsKey(it) }
      }
    } while (!reps.isEmpty() && !alphabet.isEmpty())
  } while (message.isNotEmpty())
}

private fun encrypt(plaintext: String): String {
  return plaintext.map { cipher.getIfAbsentValue(it, it) }.joinToString("")
}

private fun decrypt(ciphertext: String): String {
  return ciphertext.map {
    cipher.inverse().getIfAbsentValue(it,
      it)
  }.joinToString("")
}
