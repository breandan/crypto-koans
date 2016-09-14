import org.eclipse.collections.api.multimap.MutableMultimap
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap
import org.eclipse.collections.impl.multimap.list.FastListMultimap
import java.io.File
import java.util.*

val dictionary: HashSet<String> = HashSet()
val patternDict: MutableMultimap<String, String> = FastListMultimap.newMultimap()
val ciphertext: MutableMultimap<String, String> = FastListMultimap.newMultimap()
var originalCT: String = ""
var bestGuess: String = ""

fun main(args: Array<String>) {
  var sc = Scanner(File("src/main/resources/google-10000-english.txt"))
  val lines = ArrayList<String>()
  while (sc.hasNextLine()) {
    lines.add(sc.nextLine())
  }

  lines.forEach {
    patternDict.put(convertWordToPattern(it), it)
    dictionary.plusAssign(it)
  }

  sc = Scanner(File("src/main/resources/ciphertext"))
  sc.useDelimiter(" ")

  val sb: StringBuilder = StringBuilder()

  while (sc.hasNext()) {
    val s = sc.next()
    if (!ciphertext.containsKey(s))
      ciphertext.putAll(s, patternDict.get(convertWordToPattern(s)))
    sb.append(s + " ")
  }

  val cipherTextString = sb.toString()
  println(cipherTextString)
  bestGuess = cipherTextString
  originalCT = cipherTextString

  ciphertext.forEachKey {
    val t = ciphertext.get(it).toList()
    println(it + " -> " + t)
  }

  pairwise()
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
  var iterations = 0
  var lastCipherMapSize = 0
  val letterMap: HashBagMultimap<Char, Char> = HashBagMultimap.newMultimap()

  for (i in 'a'..'z') {
    for (j in 'a'..'z') {
      letterMap.put(i, j)
    }
  }

  while (ciphertext.size() != lastCipherMapSize) {
    lastCipherMapSize = ciphertext.size()
    for (entry in ciphertext.keyMultiValuePairsView()) {
      val toRemoveWords: HashMap<String, String> = HashMap()
      val toAddLetters: HashBagMultimap<Char, Char> = HashBagMultimap.newMultimap()
      for (word in entry.two) {
        for (i in 0..word.length - 1) {
          if (!letterMap.containsKeyAndValue(entry.one[i], word[i])) {
            toRemoveWords.put(entry.one, word)
            break
          }

          if (word[i].isLetter()) {
            toAddLetters.put(entry.one[i], word[i])
          }
        }
      }
      toAddLetters.forEachKeyMultiValues { cipherLetter, newLetters ->
        letterMap.putAll(cipherLetter,
          letterMap.removeAll(cipherLetter).intersect(newLetters))
      }
      toRemoveWords.forEach { key, value ->
        ciphertext.remove(key, value)

        // Try to solve for proper nouns, but let's indicate with CAPS
        if (ciphertext[key].isEmpty)
          ciphertext.put(key,
            key.map { letterMap[it].first().toUpperCase() }.joinToString(""))
      }
    }
    iterations++
  }

  originalCT.split(" ").filter { !it.isEmpty() }.forEach {
    println(ciphertext.get(it).toString() + " -> (" + it + ")")
  }
}

fun convertWordToPattern(word: String): String {
  val letters: HashMap<Char, Char> = HashMap()

  return String(word.map {
    letters.computeIfAbsent(it,
      { (letters.size + 97).toChar() })
  }.toCharArray())
}