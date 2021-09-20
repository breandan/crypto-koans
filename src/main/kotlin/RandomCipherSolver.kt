import org.eclipse.collections.impl.multimap.bag.HashBagMultimap
import org.eclipse.collections.impl.multimap.list.FastListMultimap
import java.io.File
import java.util.*

private val possibleWords = FastListMultimap.newMultimap<String, String>()

fun main() {
  val sc = File("src/main/resources/google-10000-english.txt")
  val lines = sc.readLines()

  val patterns = FastListMultimap.newMultimap<String, String>()
  lines.forEach { patterns.put(convertWordToPattern(it), it) }

  val ciphertext = File("src/main/resources/ciphertext")
    .readText().split(" ").joinToString(" ") { s ->
      if (!possibleWords.containsKey(s))
        possibleWords.putAll(s, patterns.get(convertWordToPattern(s)))
      s.lowercase()
    }

  println(ciphertext)

  pairwise()

  ciphertext.split(" ").filter { it.isNotEmpty() }.forEach {
    println(possibleWords.get(it).toString() + " -> (" + it + ")")
  }
}

/**
 * The following algorithm uses two primary data structures, a dictionary of
 * word mappings, and a dictionary of possible letter mappings (initially
 * full). Considering each ciphertext word, first let's build a map of all
 * the possible English words the cipherword could represent. Ex. "eddm"
 * might map to "loop", "pool", "reek", and therefor 'e' would map to 'l',
 * 'p', 'r'. If we assume our dictionary contains a complete list of possible
 * word mappings (ie. no plaintext word is unlisted), then in this example,
 * 'e' could *never* map to 'x'. So if we should ever see a word in our
 * dictionary containing the letter 'x' at the same index as the letter 'e'
 * in the cipherword, then we can be certain this word is not contained in
 * the plaintext. Furthermore, if we should ever encounter a plaintext word
 * in the dictionary which has a new letter in the same position as a known
 * cipherletter, we can immediately discard this word from the dictionary.
 * For each word, if the cipherletter is completely new, then we will put
 * every possible corresponding plaintext letter for this cipherword into the
 * letter map. The algorithm then proceeds to filter the word dictionary
 * using the updated letter mapping, and then update the letter map, back and
 * forth, until the dictionary stops shrinking. The resulting dictionary will
 * contain no cipherletter collisions, and if we have enough text, should
 * approximate the plaintext message.
 */

private fun pairwise(alphabet: Iterable<Char> = 'a'..'z') {
  var lastDictionarySize = 0
  val candidates = HashBagMultimap.newMultimap<Char, Char>()

  for (i in alphabet) for (j in alphabet) candidates.put(i, j)

  while (possibleWords.size() != lastDictionarySize) {
    lastDictionarySize = possibleWords.size()
    for (entry in possibleWords.keyMultiValuePairsView()) {
      val token = entry.one
      val impossibleWords = HashSet<String>()
      val seen = HashBagMultimap.newMultimap<Char, Char>()

      for (word in entry.two) {
        for (i in word.indices) {
          if (!candidates.containsKeyAndValue(token[i], word[i])) {
            impossibleWords.add(word)
            break
          }

          seen.put(token[i], word[i])
        }
      }

      // Filter letter map against all possible letter mappings
      seen.forEachKeyMultiValues { cipherLetter, newChars ->
        candidates.putAll(cipherLetter,
          candidates.removeAll(cipherLetter)
            .intersect(newChars))
      }

      // Discard all impossible words
      impossibleWords.forEach { word ->
        possibleWords.remove(token, word)

        // Try to solve for proper nouns, but let's indicate with CAPS
        if (possibleWords[token].isEmpty)
          possibleWords.put(token,
            token.map { candidates[it].first().uppercaseChar() }.joinToString(""))
      }
    }
  }
}

private fun convertWordToPattern(word: String, m: HashMap<Char, Char> = HashMap()): String =
  word.map { m.computeIfAbsent(it) { 'a' + m.size } }.joinToString("")