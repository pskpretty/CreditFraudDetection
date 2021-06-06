package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

object TestFactory {
    fun createCardTransaction(): List<Transaction> {
        return listOf(Transaction("10d7ce2f43e35fa57d1bbf8b1e2",
            LocalDateTime.parse("2014-04-29T13:15:54"), BigDecimal.valueOf(50L)))
    }
}