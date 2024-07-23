package org.griotold.concert.domain.queue

import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenGenerator {

    fun generate(): String {
        return UUID.randomUUID().toString()
    }
}