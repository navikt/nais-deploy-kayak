object AssertJ {
    const val version = "3.16.1"
    const val core = "org.assertj:assertj-core:$version"
    fun library(name: String) = "org.assertj:assertj-$name:$version"
}

object Avro {
    const val version = "1.9.1"
    const val avro = "org.apache.avro:avro:$version"
    fun library(artifactId: String) = "org.apache.avro:$artifactId:$version"
}

object Confluent {
    const val version = "5.5.0"
    const val avroSerializer = "io.confluent:kafka-avro-serializer:$version"
}

object Kafka {
    const val version = "2.6.0"
    const val clients = "org.apache.kafka:kafka-clients:$version"
}

object KafkaEmbedded {
    const val version = "2.4.0"
    const val env = "no.nav:kafka-embedded-env:$version"
}
object Konfig {
    const val konfig = "com.natpryce:konfig:1.6.10.0"
}
object Kotlin {
    const val version = "1.4.0"
    object Logging {
        const val version = "1.8.3"
        const val kotlinLogging = "io.github.microutils:kotlin-logging:$version"
    }
    object Serialization {
        const val version = "1.0-M1-1.4.0-rc"
        const val runtime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:$version"
    }
}

object Ktor {
    const val version = "1.3.2-1.4.0-rc"
    const val groupId = "io.ktor"
    fun server(subComponent: String) = "$groupId:ktor-server-$subComponent:$version"
    fun metrics(subComponent: String) = "$groupId:ktor-metrics-$subComponent:$version"
    const val serialization = "$groupId:ktor-serialization:$version"
}

object Log4j2 {
    const val version = "2.13.3"
    const val groupId = "org.apache.logging.log4j"
    const val api = "$groupId:log4j-api:$version"
    const val core = "$groupId:log4j-core:$version"
    const val slf4j = "$groupId:log4j-slf4j-impl:$version"
    object Logstash {
        private const val version = "1.0.3"
        const val logstashLayout = "com.vlkan.log4j2:log4j2-logstash-layout-fatjar:$version"
    }
}

object Micrometer {
    const val version = "1.5.4"
    const val prometheusRegistry = "io.micrometer:micrometer-registry-prometheus:$version"
}
object Nada {
    const val version = "1.0.4"
    const val devRapidSchema = "no.nav.nada:nada-devrapid-schema:$version"
}
object Prometheus {
    const val version = "0.8.0"
    fun library(name: String) = "io.prometheus:simpleclient_$name:$version"
}

object Protobuf {
    const val version = "3.11.3"
    const val java = "com.google.protobuf:protobuf-java:$version"
}

object Shadow {
    const val version = "5.2.0"
    const val shadow = "com.github.johnrengelman.shadow"
}
object Spotless {
    const val version = "3.27.1"
    const val spotless = "com.diffplug.gradle.spotless"
}