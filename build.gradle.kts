plugins {
  application
  kotlin("jvm") version "1.7.20-RC"
  id("com.github.ben-manes.versions") version "0.42.0"
}

application {
  mainClass.set("HelloCryptoKt")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("org.eclipse.collections:eclipse-collections-api:11.1.0")
  implementation("org.eclipse.collections:eclipse-collections:11.1.0")
  implementation("ai.hypergraph:kaliningraph")
}