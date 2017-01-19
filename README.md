# crypto-exercises

Here we demonstrate some simple ciphers and how to break them. This repository contains sample code, implementing the following ciphers:

* [Caesar cipher](https://en.wikipedia.org/wiki/Caesar_cipher)
* [Isogram cipher](https://en.wikipedia.org/wiki/Isogram#Uses_in_ciphers)
* [Mixed alphabet (random) cipher](http://crypto.interactive-maths.com/mixed-alphabet-cipher.html)
* [One-time pad](https://en.wikipedia.org/wiki/One-time_pad)

Exercises can be compiled and run using [Maven](https://maven.apache.org/), or with [IntelliJ IDEA](https://www.jetbrains.com/idea/).

## Building

First ensure [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) and [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git) are installed and run the following command in your terminal.

```
git clone https://github.com/breandan/crypto-exercises && cd crypto-exercises && ./gradlew runIdea
```

After several minutes, a program called "IntelliJ IDEA" should start. If you receive an error, make sure JAVA_HOME is [correctly set](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/). Once the program loads, right click on the file called *HelloCrypto.kt* and select "Run". You should see the following output.

```
Let's learn about cryptology!
```
