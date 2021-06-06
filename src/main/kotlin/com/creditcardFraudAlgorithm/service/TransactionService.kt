package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.Transaction
import java.io.IOException
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TransactionService {
     fun readTransactionsFromFile(inputPath: String): List<Transaction> {
        var lines = emptyList<String>()
        try {
            lines = Files.readAllLines(Paths.get(inputPath), StandardCharsets.UTF_8)
        } catch (e: IOException) {
            throw IOException("error while reading lines from file $e\"")
        }
        var transactionList = mutableListOf<Transaction>()

        lines.forEach {
            val item = it.parseTransactionSequence()
            transactionList.add(item)
        }
        return transactionList
    }

    private fun String.parseTransactionSequence(): Transaction {
        val items = this.replace("\\s".toRegex(), "").split(",")
        val cardNumber = items[0]
        val transactionDate: LocalDateTime = LocalDateTime.parse(items[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val amount = BigDecimal(items[2])
        return Transaction(cardNumber, transactionDate, amount)
    }

    fun listFraudCardNumbers(fraudCardNumbers:Set<String>)
    {
        if(fraudCardNumbers.isEmpty()) {
            println("No fraud detected in the transactions.")
        }
        fraudCardNumbers.forEach {
            println("Fraud detected on hashed card number $it")

        }
    }
}