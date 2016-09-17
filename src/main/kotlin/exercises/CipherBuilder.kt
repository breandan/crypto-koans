package exercises

import org.eclipse.collections.impl.bimap.mutable.HashBiMap

private val cipher = HashBiMap<Char, Char>()

fun main(args: Array<String>) {
  var alphabet = "etaoinshrdlcumwfgypbvkjxqz"
  do {
    val msg = prompt("Enter a new message: ")

    do {
      println("Encrypted message:   " + encrypt(msg))
      println("Cipher key: " + cipher)
      println()
      println("Unencrypted letters:  " +
        msg.filter { !cipher.containsKey(it) && it.isLetterOrDigit() }
          .toCharArray()
          .distinct()
          .joinToString(""))
      println("Unassigned alphabet:  " + alphabet)
      val reps = prompt("Enter replacement: ")

      var a = reps.find { (msg.contains(it) || msg.isEmpty()) && it.isLetterOrDigit() }
      var c = reps;
      while (a != null) {
        val b = c.replace(a.toString(), "").find { it.isLetterOrDigit() }

        if (b != null) {
          if (cipher.inverse().containsKey(b)) {
            println("Evicting " + a + b)
            cipher.remove(a)
          }
          cipher.put(a, b)
          alphabet = alphabet.replace(b.toString(), "")
        }

        val collisions = cipher.valuesView().filter {
          msg.contains(it) && !cipher.containsKey(it)
        }
        if (collisions.isNotEmpty() && c.isEmpty()) {
          println("To assign: " + collisions)
          println("(Decryption will fail unless you reassign these letters!)")
        }

        c = c.replaceFirst(a.toString(), "")
          .replaceFirst(b.toString(), "")
        a = c.find { (msg.contains(it) || msg.isEmpty()) && it.isLetterOrDigit() }
      }
    } while (!reps.isEmpty() && !alphabet.isEmpty())
  } while (msg.isNotEmpty())
}

private fun encrypt(plaintext: String): String {
  return plaintext.map { cipher.getIfAbsentValue(it, it) }.joinToString("")
}

private fun decrypt(ciphertext: String): String {
  return ciphertext.map { cipher.inverse().getIfAbsentValue(it, it) }
    .joinToString("")
}
