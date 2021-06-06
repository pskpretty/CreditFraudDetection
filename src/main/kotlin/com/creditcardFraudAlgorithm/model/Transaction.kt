package com.creditcardFraudAlgorithm.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    val hashCardNumber:String,
    val timestamp:LocalDateTime,
    val amount:BigDecimal
)