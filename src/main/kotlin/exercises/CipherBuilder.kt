package exercises

import org.eclipse.collections.impl.bimap.mutable.HashBiMap

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
        message.filter { !cipher.containsKey(it) && it.isLetterOrDigit() }
          .toCharArray()
          .distinct()
          .joinToString(""))
      println("Unassigned alphabet:  " + alphabet)
      val reps = prompt("Enter replacement: ")

      var a = reps.find(function(message))
      var c = reps
      while (a != null) {
        val b = c.replace(a.toString(), "").find { it.isLetterOrDigit() }

        if (b != null) {
          if (cipher.containsValue(b)) {
            println("Evicting " + b)
            cipher.inverse().remove(b)
          }
          cipher.put(a, b)
          alphabet = alphabet.replace(b.toString(), "")
        }

        val collisions = cipher.valuesView().filter {
          message.contains(it) && !cipher.containsKey(it)
        }

        if (collisions.isNotEmpty() && c.isEmpty()) {
          println("To assign: " + collisions)
          println("(Decryption will fail unless you reassign these letters!)")
        }

        c = c.replaceFirst(a.toString(), "").replaceFirst(b.toString(), "")
        a = c.find(function(message))
      }
    } while (!reps.isEmpty() && !alphabet.isEmpty())
  } while (message.isNotEmpty())
}

private fun function(msg: String): (Char) -> Boolean = {
  (msg.contains(it) || msg.isEmpty()) && it.isLetterOrDigit()
}

private fun encrypt(plaintext: String): String {
  return plaintext.map { cipher.getIfAbsentValue(it, it) }.joinToString("")
}

private fun decrypt(ciphertext: String): String {
  return ciphertext.map { cipher.inverse().getIfAbsentValue(it, it) }.joinToString("")
}
