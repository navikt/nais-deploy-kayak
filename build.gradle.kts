import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version(Kotlin.version)
    kotlin("plugin.serialization").version(Kotlin.version)
    application
    id(Shadow.shadow).version(Shadow.version)
}

application {
    applicationName = "nais-deploy-kayak"
    mainClassName = "no.nav.nada.NaisDeployKayakKt"
}

repositories {
    jcenter()
    mavenCentral()
    maven {
        name = "nav-gpr"
        url = uri("https://maven.pkg.github.com/navikt")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: "x-access-token"
            password = project.findProperty("gpr.password") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation(Kotlin.Serialization.runtime)
    // Http Server
    implementation(Ktor.server("netty"))
    implementation(Ktor.metrics("micrometer"))
    implementation(Ktor.serialization)

    // Logging
    implementation(Kotlin.Logging.kotlinLogging)
    implementation(Log4j2.api)
    implementation(Log4j2.core)
    implementation(Log4j2.slf4j)
    implementation(Log4j2.Logstash.logstashLayout)

    // Prometheus / Metrics
    implementation(Prometheus.library("hotspot"))
    implementation(Prometheus.library("common"))
    implementation(Prometheus.library("log4j2"))
    implementation(Micrometer.prometheusRegistry)
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

tasks.named("shadowJar") {
    dependsOn("test")
}

tasks.named("jar") {
    dependsOn("test")
}