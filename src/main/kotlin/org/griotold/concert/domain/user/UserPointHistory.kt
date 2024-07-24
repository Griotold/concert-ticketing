package org.griotold.concert.domain.user

import org.griotold.concert.domain.common.type.TransactionType

data class UserPointHistory(
    val amount: Int,
    val type: TransactionType
)
