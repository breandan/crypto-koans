package extras

import java.math.BigInteger
import java.math.BigInteger.*

fun ackermann(m: BigInteger, n: BigInteger): BigInteger =
  if (m == ZERO) n + ONE
  else if (n == ZERO) ackermann(m - ONE, ONE)
  else ackermann(m - ONE, ackermann(m, n - ONE))

fun main() {
  for(i in 0L..100L) {
    ackermann(TWO, valueOf(i)).also { println(it) }
      .isProbablePrime(1).also { println(it) }
  }
}