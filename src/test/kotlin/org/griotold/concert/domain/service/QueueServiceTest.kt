package org.griotold.concert.domain.service

import org.griotold.concert.domain.repository.QueueRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class QueueServiceTest {

    @Mock
    private lateinit var queueRepository: QueueRepository

    @InjectMocks
    private lateinit var queueService: QueueService

    @Nested
    @DisplayName("대기열 발급")
    inner class IssueTest {

    }

    @Nested
    @DisplayName("대기열 조회")
    inner class RetrieveTest {

    }
}