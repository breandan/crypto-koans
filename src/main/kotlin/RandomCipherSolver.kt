import org.eclipse.collections.api.multimap.MutableMultimap
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap
import org.eclipse.collections.impl.multimap.list.FastListMultimap
import java.io.File
import java.util.*

private val d: MutableMultimap<String, String> = FastListMultimap.newMultimap()

fun main(args: Array<String>) {
  var sc = Scanner(File("src/main/resources/google-10000-english.txt"))
  val lines = ArrayList<String>()
  while (sc.hasNextLine()) {
    lines.add(sc.nextLine())
  }

  val patternDict: MutableMultimap<String, String> = FastListMultimap.newMultimap()
  lines.forEach {
    patternDict.put(convertWordToPattern(it), it)
  }

  sc = Scanner(File("src/main/resources/ciphertext"))
  sc.useDelimiter(" ")

  val sb = StringBuilder()

  while (sc.hasNext()) {
    val s = sc.next()
    if (!d.containsKey(s))
      d.putAll(s, patternDict.get(convertWordToPattern(s)))
    sb.append("$s ")
  }

  val ciphertext = sb.toString()
  println(ciphertext)

  pairwise()

  ciphertext.split(" ").filter { !it.isEmpty() }.forEach {
    println(d.get(it).toString() + " -> (" + it + ")")
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

fun pairwise() {
  var lastDictionarySize = 0
  val candidates: HashBagMultimap<Char, Char> = HashBagMultimap.newMultimap()

  for (i in 'a'..'z') {
    for (j in 'a'..'z') {
      candidates.put(i, j)
    }
  }

  while (d.size() != lastDictionarySize) {
    lastDictionarySize = d.size()
    for (entry in d.keyMultiValuePairsView()) {
      val token = entry.one
      val impossibleWords: HashSet<String> = HashSet()
      val seen: HashBagMultimap<Char, Char> = HashBagMultimap.newMultimap()

      for (word in entry.two) {
        for (i in 0 until word.length) {
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
          candidates.removeAll(cipherLetter).intersect(newChars))
      }

      // Discard all impossible words
      impossibleWords.forEach { word ->
        d.remove(token, word)

        // Try to solve for proper nouns, but let's indicate with CAPS
        if (d[token].isEmpty)
          d.put(token,
            token.map { candidates[it].first().toUpperCase() }.joinToString(""))
      }
    }
  }
}

private fun convertWordToPattern(word: String): String {
  val letters: HashMap<Char, Char> = HashMap()

  return word.map {
    letters.computeIfAbsent(it) { 'a' + letters.size }
  }.joinToString("")
}