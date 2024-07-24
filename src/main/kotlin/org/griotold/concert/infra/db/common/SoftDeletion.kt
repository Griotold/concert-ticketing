package org.griotold.concert.infra.db.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.ZonedDateTime

@Embeddable
class SoftDeletion {

    @Column(name = "deleted_at")
    var deletedAt: ZonedDateTime? = null
        protected set

    fun delete() {
        deletedAt = ZonedDateTime.now()
    }

    fun restore() {
        deletedAt = null
    }
}