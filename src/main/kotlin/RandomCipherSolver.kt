import org.eclipse.collections.api.multimap.Multimap
import org.eclipse.collections.api.multimap.MutableMultimap
import org.eclipse.collections.impl.multimap.list.FastListMultimap
import org.eclipse.collections.impl.utility.StringIterate
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.comparisons.compareBy

val dictionary: HashSet<String> = HashSet()
val patternDict: MutableMultimap<String, String> = FastListMultimap.newMultimap()
val ciphertext: MutableMultimap<String, String> = FastListMultimap.newMultimap()
var bestGuess: String = "";

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

  ciphertext.forEachKey {
    val t = ciphertext.get(it).toList()
    println(it + " -> " + t)
  }

  val cipherTextCharsSortedByFrequency: MutableMap<Char, AtomicInteger> = HashMap()
  val cpl = StringIterate.toCodePointList(cipherTextString)

  cpl.collectInt {
    if (it.toChar().isLetter()) {
      val ai = cipherTextCharsSortedByFrequency[it.toChar()]
      if (ai != null) {
        ai.andIncrement
      } else
        cipherTextCharsSortedByFrequency[it.toChar()] = AtomicInteger(1)
    }
    it
  }

  var uniqueCipherTextCharactersSortedByFrequency = cipherTextCharsSortedByFrequency.entries.sortedWith(compareBy({ it.value.get() })).asReversed()
  var englishLettersByFrequency = "etaoinshrdlcumwfgypbvkjxqz"

  println("Unique ciphertext chars sorted by frequency: " + uniqueCipherTextCharactersSortedByFrequency)

  recurse(cipherTextString, ciphertext, uniqueCipherTextCharactersSortedByFrequency.joinToString { it.key.toString() }.replace(", ", ""), englishLettersByFrequency)
}

fun recurse(cipherText: String, cipherMap: Multimap<String, String>, cipherLetters: String, alphabet: String) {
  if (scoreResult(cipherText) > scoreResult(bestGuess)) {
    bestGuess = cipherText
    println(bestGuess)
  }

  if (cipherMapContainsEmptyPattern(cipherMap))
    return

  if (cipherLetters.isEmpty()) {
//    println(cipherText)
    return
  }

  val longestSingleton = getLongestSingleton(cipherMap)

  if (longestSingleton != null) {
    val newCipherText = replaceAll(cipherText, longestSingleton.first, longestSingleton.second)
    val newCipherMap = removeNonMatchingCharactersFromCipherMap(longestSingleton.first, longestSingleton.second, cipherMap)
    val newCipherLetters = replaceAll(cipherLetters, longestSingleton.first, "")
    val newAlphabet = replaceAll(alphabet, longestSingleton.second, "")

    recurse(newCipherText, newCipherMap, newCipherLetters, newAlphabet)
  } else {
    val candidateScoreMap: MutableMap<Char, Int> = HashMap()

    for (candidate in alphabet) {
      var score = 0
      for (entry in cipherMap.keyMultiValuePairsView()) {
        val idxOfFirstChar = entry.one.indexOfFirst { it.equals(cipherText[0]) }
        if (idxOfFirstChar >= 0)
          for (word in entry.two)
            if (idxOfFirstChar == word.indexOfFirst { it.equals(candidate) })
              score++
      }

      candidateScoreMap.put(candidate, score)
    }

    val resortedAlphabet = candidateScoreMap.entries.sortedWith(compareBy({ it.value })).asReversed().toString().filter { it.isLetter() }
//    println(resortedAlphabet)

    for (ix: Char in resortedAlphabet) {
      val candidateCipherLetter: Char = cipherLetters[0]
      val candidateReplaceLetter: Char = ix
      val newCipherText = replaceAll(cipherText, candidateCipherLetter + "", candidateReplaceLetter + "")
      val newCipherMap = removeNonMatchingCharactersFromCipherMap(candidateCipherLetter + "", candidateReplaceLetter + "", cipherMap)
      val newCipherLetters = cipherLetters.substring(1)
      val newAlphabet = replaceAll(alphabet, candidateReplaceLetter + "", "")

      recurse(newCipherText, newCipherMap, newCipherLetters, newAlphabet)
    }
  }
}

fun scoreResult(cipherText: String): Int {
  var score = 0
  cipherText.split(" ").forEach { if (dictionary.contains(it.toLowerCase())) score++ }
  return score
}

fun replaceAll(text: String, sourceLetters: String, replaceLetters: String): String {
  var i = 0;
  var replacedText = text;
  for (letter in sourceLetters) {
    if (i < replaceLetters.length)
      replacedText = replacedText.replace(letter, replaceLetters[i].toUpperCase())
    else
      replacedText = replacedText.replace(letter + "", "")
    i++
  }

  return replacedText
}

fun getLongestSingleton(cipherMap: Multimap<String, String>): Pair<String, String>? {
  var championSingleton = Pair("", "")
  for (s in cipherMap.keyMultiValuePairsView()) {
    if (s.two.size() == 1 && s.one.length > championSingleton.first.length) {
      championSingleton = Pair(s.one, s.two.first())
    }
  }

  if (championSingleton.first.isEmpty())
    return null;

  return championSingleton;
}

fun removeNonMatchingCharactersFromCipherMap(cipherWord: String, realWord: String, cipherMap: Multimap<String, String>): MutableMultimap<String, String> {
  var i = 0;
  val cipherMapCopy: MutableMultimap<String, String> = FastListMultimap.newMultimap()
  for (cipherLetter in cipherWord) {
    val realLetter = realWord[i]
    for (entry in cipherMap.keyMultiValuePairsView()) {
      for (word in entry.two) {
        if (entry.one.indexOf(cipherLetter) == word.indexOf(realLetter))
          cipherMapCopy.put(entry.one, word)
//        else
//          println("Nonmatching: (" + entry.one + ", " +  word + ")")
      }
    }
    i++;
  }

//  println("(" + cipherWord + "->" + realWord + ")" + "reduced from " + cipherMap.size() + " to " + cipherMapCopy.size())

  return cipherMapCopy
}

fun cipherMapContainsEmptyPattern(cipherMap: Multimap<String, String>): Boolean {
  if (cipherMap.keysView().size() != ciphertext.keysView().size()) {
    return true
  }
  for (s in cipherMap.multiValuesView()) {
    if (s.isEmpty)
      return true
  }

  return false
}

fun convertWordToPattern(word: String): String {
  var sb: StringBuilder = StringBuilder()
  var letters: ArrayList<Char> = ArrayList()

  word.toCharArray().forEach {
    var idx: Int = letters.indexOf(it)
    if (idx == -1) {
      letters.add(it)
      idx = letters.size - 1
    }

    val char: Char = (97 + idx).toChar()
    sb.append(char)
  }

  return sb.toString()
}
