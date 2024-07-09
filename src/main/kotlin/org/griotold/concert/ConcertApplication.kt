package org.griotold.concert

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ConcertApplication

fun main(args: Array<String>) {
    runApplication<ConcertApplication>(*args)
}
