package no.nav.nada

import com.natpryce.konfig.ConfigurationMap
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType
import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import java.util.Properties

private val local = ConfigurationMap(
    mapOf(
        "application.port" to "9090",
        "confluent.bootstrap" to "",
        "confluent.username" to "",
        "confluent.topic" to ""
    )
)

private val dev = ConfigurationMap(
    mapOf(
        "application.port" to "9090"
    )
)
private val prod = ConfigurationMap(
    mapOf(
        "application.port" to "9090"
    )
)
data class Application(val port: Int = config()[Key("application.port", intType)])
data class Confluent(val bootstrapServer: String = config()[Key("confluent.bootstrap", stringType)],
                     val username: String = config()[Key("confluent.username", stringType)],
                     val password: String = config()[Key("confluent.password", stringType)],
                     val topic: String = config()[Key("confluent.topic", stringType)]) {
    fun toConsumerProps(): Properties {
        return Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        }
    }
}
data class DevRapid(val bootstrapServer: String = config()[Key("aiven.bootstrap", stringType)],
                    val username: String = config()[Key("aiven.username", stringType)],
                    val password: String = config()[Key("aiven.password", stringType)],
                    val topic: String = config()[Key("aiven.topic", stringType)],
                    val schemaRegistry: String = config()[Key("aiven.schemaregistry", stringType)]) {
    fun toProducerProps(): Properties {
        return Properties().apply {
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
            put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistry)
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java)
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer::class.java)
            put(ProducerConfig.ACKS_CONFIG, "1")
        }
    }
}

data class Configuration(val application: Application = Application(),
                         val confluent: Confluent = Confluent(),
                         val devRapid: DevRapid = DevRapid())

fun config() = when (System.getenv("NAIS_CLUSTER_NAME")) {
    "dev-gcp" -> ConfigurationProperties.systemProperties() overriding EnvironmentVariables overriding dev
    "prod-gcp" -> ConfigurationProperties.systemProperties() overriding EnvironmentVariables overriding prod
    else -> ConfigurationProperties.systemProperties() overriding EnvironmentVariables overriding local
}