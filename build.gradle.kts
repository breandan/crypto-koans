plugins {
  application
  kotlin("jvm") version "1.5.30"
}

application {
  mainClass.set("HelloCryptoKt")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.eclipse.collections:eclipse-collections-api:9.2.0")
  implementation("org.eclipse.collections:eclipse-collections:9.2.0")
}