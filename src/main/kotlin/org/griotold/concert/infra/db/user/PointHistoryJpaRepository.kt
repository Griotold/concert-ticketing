package org.griotold.concert.infra.db.user

import org.springframework.data.jpa.repository.JpaRepository

interface PointHistoryJpaRepository : JpaRepository<PointHistoryEntity, Long>
