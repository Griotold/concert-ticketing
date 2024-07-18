package org.griotold.concert.domain.common

class WithPage<T>(
    val list: List<T>,
    val pageInfo: PageInfo,
)