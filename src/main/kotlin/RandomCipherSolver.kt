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

//  ciphertext.forEachKey {
//    val t = ciphertext.get(it).toList()
//    println(it + " -> " + t)
//  }

//  val cipherTextCharsSortedByFrequency: MutableMap<Char, AtomicInteger> = HashMap()
//  val cpl = StringIterate.toCodePointList(cipherTextString)
//
//  cpl.collectInt {
//    if (it.toChar().isLetter()) {
//      val ai = cipherTextCharsSortedByFrequency[it.toChar()]
//      if (ai != null) {
//        ai.andIncrement
//      } else
//        cipherTextCharsSortedByFrequency[it.toChar()] = AtomicInteger(1)
//    }
//    it
//  }
//
//  var uniqueCipherTextCharactersSortedByFrequency = cipherTextCharsSortedByFrequency.entries.sortedWith(compareBy({ it.value.get() })).asReversed()
//  var englishLettersByFrequency = "etaoinshrdlcumwfgypbvkjxqz"
//
//  println("Unique ciphertext chars sorted by frequency: " + uniqueCipherTextCharactersSortedByFrequency)
//  println(cipherTextString)

//  recurse(cipherTextString, ciphertext, uniqueCipherTextCharactersSortedByFrequency.joinToString { it.key.toString() }.replace(", ", ""), englishLettersByFrequency)
  pairwise()
}

fun pairwise() {
  val charmap: MutableMultimap<Char, Char> = FastListMultimap.newMultimap()
  var lastCipherMapSize = 0
  while (ciphertext.size() != lastCipherMapSize) {
    lastCipherMapSize = ciphertext.size()
    for (entry in ciphertext.keyMultiValuePairsView()) {
      var toRemoveWords: HashMap<String, String> = HashMap()
      var toAddChars: MutableMultimap<Char, Char> = FastListMultimap.newMultimap()
      for (word in entry.two) {
        var i = 0
        for (char in word) {
          if (charmap.containsKey(entry.one[i]) && !charmap.containsKeyAndValue(entry.one[i], char)) {
            toRemoveWords.put(entry.one, word)
            break
          }
          if (char.isLetter())
            toAddChars.put(entry.one[i], char)

          i++
        }
      }
      toAddChars.forEachKeyMultiValues { key, value ->
        if (charmap.containsKey(key)) {
          charmap.putAll(key, charmap.removeAll(key).intersect(value))
        } else
          charmap.putAll(key, value)
      }
      toRemoveWords.forEach { key, value -> ciphertext.remove(key, value) }
    }
  }

  originalCT.split(" ").filter { !it.isEmpty() }.forEach { println(ciphertext.get(it).toString() + " -> (" + it + ")") }

//  println(ciphertext.keysView().size())
//  println(ciphertext.size())
}

//var lastLongestSingleton = ""
//fun recurse(cipherText: String, cipherMap: Multimap<String, String>, cipherLetters: String, alphabet: String) {
//  val subTable = getCurrentSubstitutionTableFromCipherText(originalCT, cipherText)
//
//  if (scoreResult(cipherText) > scoreResult(bestGuess)) {
//    bestGuess = cipherText
//    println(subTable)
//    println(bestGuess)
//  }
//
//  if (cipherMapContainsEmptyPattern(cipherMap))
//    return
//
//  if (cipherLetters.isEmpty()) {
////    println(cipherText)
//    return
//  }
//
//  val longestSingleton = getLongestSingleton(cipherMap)
//
//  if (longestSingleton != null && !longestSingleton.second.equals(lastLongestSingleton)) {
//    val newCipherText = replaceAll(cipherText, longestSingleton.first, longestSingleton.second)
//    val newCipherMap = removeNonMatchingCharactersFromCipherMap(longestSingleton.first, longestSingleton.second, cipherMap)
//    val newCipherLetters = replaceAll(cipherLetters, longestSingleton.first, "")
//    val newAlphabet = replaceAll(alphabet, longestSingleton.second, "")
//
//    lastLongestSingleton = longestSingleton.second
//
//    recurse(newCipherText, newCipherMap, newCipherLetters, newAlphabet)
//  }
//
////  resortAlphabetByCiphermapFrequency(alphabet, cipherMap, cipherText)
//
//  for (letter: Char in alphabet) {
//    val candidateCipherLetter: Char = cipherLetters[0]
//    val candidateReplaceLetter: Char = letter
//    val newCipherText = replaceAll(cipherText, candidateCipherLetter + "", candidateReplaceLetter + "")
//    val newCipherMap = removeNonMatchingCharactersFromCipherMap(candidateCipherLetter + "", candidateReplaceLetter + "", cipherMap)
//    val newCipherLetters = cipherLetters.substring(1)
//    val newAlphabet = replaceAll(alphabet, candidateReplaceLetter + "", "")
//
//    recurse(newCipherText, newCipherMap, newCipherLetters, newAlphabet)
//  }
//
//
////  println(alphabet)
////  println(cipherText)
//}
//
//private fun resortAlphabetByCiphermapFrequency(alphabet: String, cipherMap: Multimap<String, String>, cipherText: String): String {
//  val candidateScoreMap: MutableMap<Char, Int> = HashMap()
//
//  for (candidate in alphabet) {
//    var score = 0
//    for (entry in cipherMap.keyMultiValuePairsView()) {
//      val idxOfFirstChar = entry.one.indexOfFirst { it.equals(cipherText[0]) }
//      if (idxOfFirstChar >= 0)
//        for (word in entry.two)
//          if (idxOfFirstChar == word.indexOfFirst { it.equals(candidate) })
//            score++
//    }
//
//    candidateScoreMap.put(candidate, score)
//  }
//
//  return candidateScoreMap.entries.sortedWith(compareBy({ it.value })).asReversed().toString().filter { it.isLetter() }
//}
//
//fun getCurrentSubstitutionTableFromCipherText(originalCT: String, currentCT: String): Map<Char, Char> {
//  val map = HashMap<Char, Char>()
//
//  var i = 0
//  for (letter in originalCT) {
//    if (currentCT[i].isUpperCase())
//      map.put(letter, currentCT[i])
//
//    i++
//  }
//
//  return map
//}
//
//fun scoreResult(cipherText: String): Int {
//  var score = 0
//  cipherText.split(" ").forEach { if (dictionary.contains(it.toLowerCase())) score++ }
//  return score
//}
//
//fun replaceAll(text: String, sourceLetters: String, replaceLetters: String): String {
//  var i = 0
//  var replacedText = text
//  for (letter in sourceLetters) {
//    if (i < replaceLetters.length && letter.isLowerCase())
//      replacedText = replacedText.replace(letter + "", replaceLetters[i].toUpperCase() + "")
//    else
//      replacedText = replacedText.replace(letter + "", "")
//    i++
//  }
//
//  return replacedText
//}
//
//fun getLongestSingleton(cipherMap: Multimap<String, String>): Pair<String, String>? {
//  var championSingleton = Pair("", "")
//  for (s in cipherMap.keyMultiValuePairsView()) {
//    if (s.two.size() == 1 && s.one.length > championSingleton.first.length) {
//      championSingleton = Pair(s.one, s.two.first())
//    }
//  }
//
//  if (championSingleton.first.isEmpty())
//    return null
//
//  return championSingleton
//}
//
//fun removeNonMatchingCharactersFromCipherMap(cipherWord: String, realWord: String, cipherMap: Multimap<String, String>): MutableMultimap<String, String> {
//  var i = 0
//  val cipherMapCopy: MutableMultimap<String, String> = FastListMultimap.newMultimap()
//  for (entry in cipherMap.keyMultiValuePairsView()) {
//    for (word in entry.two) {
//      val replacementWord = replaceAll(entry.one, cipherWord, realWord)
//      if (doesNotConflict(word, replacementWord)) {
//        cipherMapCopy.put(entry.one, word)
//      }
////        else
////          println("Nonmatching: (" + entry.one + ", " +  word + ")")
//    }
//    i++
//  }
//
////  println("(" + cipherWord + "->" + realWord + ")" + "reduced from " + cipherMap.size() + " to " + cipherMapCopy.size())
//
//  return cipherMapCopy
//}
//
//fun doesNotConflict(word: String, replacementWord: String): Boolean {
//  var i = 0
//  for (letter in word) {
//    if (replacementWord[i].isUpperCase() && !letter.toUpperCase().equals(replacementWord[i]))
//      return false
//    i++
//  }
//
//  return true
//}
//
//fun cipherMapContainsEmptyPattern(cipherMap: Multimap<String, String>): Boolean {
//  if (cipherMap.keysView().size() != ciphertext.keysView().size()) {
//    return true
//  }
//  for (s in cipherMap.multiValuesView()) {
//    if (s.isEmpty)
//      return true
//  }
//
//  return false
//}
//
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
