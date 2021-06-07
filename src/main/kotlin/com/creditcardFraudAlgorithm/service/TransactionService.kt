package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.Transaction
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

interface TransactionService {
    fun readTransactionsFromFile(inputPath: String): List<Transaction>
    fun listFraudCardNumbers(fraudCardNumbers: Set<String>): Set<String>
}

/**
This class is used for reading the csv file
from input and for displaying the results
to the screen
 **/
@Service
class TransactionServiceImpl : TransactionService {
    /**
    This method reads the data from text file
     * @param input path String
     **/
    override fun readTransactionsFromFile(inputPath: String): List<Transaction> {
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

    /**
    This method displays the result of fraudulent cards
     * @param fraudCardNumbers path set of String
     **/
    override fun listFraudCardNumbers(fraudCardNumbers: Set<String>): Set<String> {
        if (fraudCardNumbers.isEmpty()) {
            println("No fraud detected in the transactions.")
        }
        fraudCardNumbers.forEach {
            println("Fraud detected on hashed card number $it")

        }
        return fraudCardNumbers
    }
}