package com.creditcardFraudAlgorithm.service

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class CreditCardFraudIntegrationTest {
    private val fraudService = FraudServiceImpl()
    private val transactionService = TransactionServiceImpl()

    @Test
    fun ` should detect fraudulent Cards from transaction list`() {
        val transactions = transactionService.readTransactionsFromFile("src/test/resources/TransactionFile.txt")
        fraudService.findFraudAccounts(transactions, BigDecimal(200.0)).apply {
            assertEquals(this.size, 1)
        }
    }

    @Test
    fun ` should detect fraudulent Cards from transaction list for threshold`() {
        val transactions = transactionService.readTransactionsFromFile("src/test/resources/TransactionFile.txt")
        fraudService.findFraudAccounts(transactions, BigDecimal(100.0)).apply {
            assertEquals(this.size, 8)
        }
    }
}