package com.creditcardFraudAlgorithm.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.IOException
import java.time.format.DateTimeParseException

class TransactionServiceTest {
    private val transactionService = TransactionService()

    @Test
    fun `should throw exception when invalid amount found`() {
        assertThrows(NumberFormatException::class.java) {
            transactionService.readTransactionsFromFile("src/test/resources/IncorrectAmount")
        }
    }

    @Test
    fun `should throw exception when invalid date found`() {
        assertThrows(DateTimeParseException::class.java) {
            transactionService.readTransactionsFromFile("src/test/resources/IncorrectDate.txt")
        }
    }

    @Test
    fun `should throw exception when path cannot be detected`() {
        assertThrows(IOException::class.java) {
            transactionService.readTransactionsFromFile("")
        }
    }

    @Test
    fun `should read transactions correctly`() {
        val transactions = transactionService.readTransactionsFromFile("src/test/resources/TransactionFile.txt")
        assertEquals(transactions.size, 50)
    }
}