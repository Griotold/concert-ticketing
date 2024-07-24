package org.griotold.concert.infra.db.performance

import org.springframework.data.jpa.repository.JpaRepository

interface PerformanceJpaRepository : JpaRepository<PerformanceEntity, Long>
