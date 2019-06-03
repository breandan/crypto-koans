## How to Share a Secret Message

### What’s a secret? Something meant to be kept unknown or unseen by others.

> ***se·cret /ˈsēkrit/***
>
> *adjective*
>
> 1. not known or seen or not meant to be known or seen by others. ("how did you guess I had a secret plan?")
>
> *noun*
>
> 1. something that is kept or meant to be kept unknown or unseen by others. ("a state secret")

### Let’s say you have a secret. How do you make sure your secret will stay secret?

* Do not write it down. (Even in your diary.)
* Do not talk about it. (Even in your sleep.)
* Do not record it. (Even on your smartphone.)

### What if you need to record your secret?

#### We need to turn the secret into a message.

* How do we write (or *encode*) the secret into the message?
* How do we read (or *decode*) the message into the secret?
* How do we keep our message safe?

---
### [Exercise #1](/src/main/kotlin/exercises/4_StringExercise.kt): Let’s encode a message!

*There are many ways to encode text.
Computers often use numbers to represent characters.
We can think of a message as a long list of numbers (or one really big number).*

---

### What if you need to share your secret?

#### We can share the message in person.

* How do you know you are alone?
* Is the other person who they say they are?
* What if your friend lives far away?

#### We can share the message in public.

* This is the safest way to share a secret. Requires that we be very careful.
* If you need to share a secret message, it’s best to assume everyone can read it.
* How do we prevent those who read our message from understanding the contents?

### How can you share a secret message in public without sharing the secret?

#### You can hide the secret in a secret place

  * How do we share the location of this place?
  * Can we be sure that no one is watching us?
  * What if someone finds out our hiding spot?

#### You can hide the secret in plain sight
  * This technique is called [steganography](https://en.wikipedia.org/wiki/Steganography).
  * What happens if someone is observant?
  * What if someone learns our technique?

#### We can use a language only we understand.
  * How can we be sure no one else listening can understand our language?
  * What if someone else can read our language? They will know our secrets!
  * What if someone else can write our language? They can change our messages!

#### We can scramble up the message somehow.
  * How do we scramble or (encrypt) the message?
  * How do we unscramble or (decrypt) the message?
  * How do we keep the secret safe?

---
### [Exercise #2](/src/main/kotlin/exercises/1_SimpleCiper.kt): Let’s write a cipher!

* What is a [substitution cipher](https://en.wikipedia.org/wiki/Substitution_cipher)?
  * Substitution ciphers are (weak) encryption schemes.
  * They work by replacing symbols and words (ie. Strings) with other symbols.
  * Ciphers must be reversible (when reversed they produce the original message)

* What are some substitution ciphers?
  * [Codepoint cipher](/src/main/kotlin/ciphers/4A_CodePointCipher.kt)
  * [Caesar cipher](/src/main/kotlin/ciphers/3A_CaesarCipher.kt)
  * [Isogram cipher](/src/main/kotlin/ciphers/3B_IsogramCipher.kt)

* What is the difference between encoding and encryption?
  * Substitution ciphers are just a fancy encoding mechanism.
  * Encryption is an encoding that is difficult to decode.

---

### How safe are substitution ciphers?

#### How do we communicate using ciphers?
* We need to agree on a common method (*protocol*).
* We need to agree on a common secret (*key*).
* How do we agree on a common secret? Back to square one.

#### What is a key?

* A key is a way to keep others from knowing your secret.
* A key is something you know, have or are.
  * Something you know: a password
  * Something you have: a physical key
  * Something you are: a fingerprint
* A key, like a message, can be encoded as a number.

#### What are the risks of using this kind of cipher?
* What happens if someone guesses our protocol? They still need to guess the key.
* What happens if someone deciphers a single message? They learn they key.
* What happens if someone learns our key? They can break every message.

---
### [Exercise #3](/src/main/kotlin/exercises/3_CipherBreaker.kt): Let's break a cipher!
#### Why are substitution ciphers dangerous?
If someone learns a plaintext-ciphertext pair they can break every message.

#### Small keys are easy to guess.

* What is the only way to guarantee an unbreakable cipher?
  * One-time pad

---

### How can we improve the safety of ciphers?

* Use keys with longer text
* Use keys that are difficult to guess

### What is a prime number?

* Prime numbers have only two factors.

### What is an algorithm?

* Some set of steps for a computer to take.

---
### [Exercise #4]: Computers are fast

Let's calculate some prime numbers. How many can you calculate in 5 seconds?

---

### What makes an algorithm hard?

* Numbers are easy to multiply
* Numbers are hard to factor

### What is a random number?

* Can we get computers to generate random numbers?

---
### [Exercise #5](/src/main/kotlin/exercises/6_RSA.kt): RSA

Turns out, we can share a secret without sharing a key.

---

---
### [Exercise #6]: What is a hash function? (optional)

Hash functions are cool.

---

## Building from the source

First ensure [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) are installed and run the following command in your terminal.

```
git clone https://github.com/breandan/crypto-exercises && cd crypto-exercises && ./gradlew runIde
```

After several minutes, a program called "IntelliJ IDEA" should start. If you receive an error, make sure JAVA_HOME is [correctly set](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/). Once the program loads, right click on the file called *HelloCrypto.kt* and select "Run". You should see the following output.

```
Let's learn about cryptology!
```

## Running from the USB

### Mac OS X

Insert the provided USB drive and type or paste the following command from your terminal (<kbd>⌘</kbd>+<kbd>Space</kbd>, `🔍 Terminal`, <kbd>Enter</kbd>)

```
/Volumes/USB-STICK/idea.sh
```

This will launch an instance of IntelliJ IDEA with the project already configured.

If you see a message "Project SDK is not defined" on the top of your screen, click **Setup SDK | Configure... | + (Add new SDK)" | JDK**. Add the JDK which can be found here (under your Home directory): `~/jdk/`.

Once configured, you may need to select "Import Changes" and wait for indexing to complete.



### Windows

Insert the provided USB drive and open the folder named "Windows", then open the folder named "CryptoExercises", then double click on the icon "Launch_CryptoExercises.lnk".

### Sanity Check

You should now be able to run the `/src/main/kotlin/HelloCrypto.kt` file by right-clicking and selecting "Run".

If configured correctly, it should print the following output:

```
Let's learn about cryptology!
```
