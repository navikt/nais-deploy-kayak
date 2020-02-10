package no.nav.nada

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val konfig = Configuration()
    HealthServer.startServer(konfig.application.port).start(wait = false)
    NaisDeployConsumer.apply {
        create(konfig)
        run()
    }
}