package com.creditcardFraudAlgorithm.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class SlidingTransactionWindow(
    val transactionSubList: List<Transaction>,
    val start: LocalDateTime,
    val end: LocalDateTime
)