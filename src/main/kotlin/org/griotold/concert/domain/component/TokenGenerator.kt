package org.griotold.concert.domain.component

import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenGenerator {

    fun generateToken(memberId: Long): String {
        val input = memberId.toString()
        return UUID.nameUUIDFromBytes(input.toByteArray()).toString()
    }
}