package no.nav.nada

import io.prometheus.client.Counter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.RetriableException
import java.time.Duration
import java.time.temporal.ChronoUnit
import kotlin.coroutines.CoroutineContext

object NaisDeployConsumer : CoroutineScope {
    val logger = KotlinLogging.logger {}
    lateinit var job: Job
    lateinit var config: Configuration
    val MESSAGES_RECEIVED = Counter.build().name("deploy_melding_mottatt").help("Deploy melding mottatt").register()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun cancel() {
        job.cancel()
    }

    fun isRunning(): Boolean {
        logger.trace { "Asked if running" }
        return job.isActive
    }

    fun create(config: Configuration) {
        this.job = Job()
        this.config = config
    }

    fun run() {
        launch {
            logger.info("Starter kafka consumer")
            KafkaConsumer<String, String>(config.confluent.toConsumerProps()).use { consumer ->
                consumer.subscribe(listOf(config.confluent.topic))
                while (job.isActive) {
                    try {
                        val records = consumer.poll(Duration.of(100, ChronoUnit.MILLIS))
                        records.asSequence()
                            .onEach { logger.info { it } }
                            .onEach { MESSAGES_RECEIVED.inc() }
                            .forEach { r -> handleDeployMessage(r.value()) }
                    } catch (e: RetriableException) {
                        logger.warn("Had a retriable exception, retrying", e)
                    }
                }
            }
        }
    }

    suspend fun handleDeployMessage(msg: String): Unit {

    }
}