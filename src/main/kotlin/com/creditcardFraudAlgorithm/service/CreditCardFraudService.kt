package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.Transaction
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal
import java.time.format.DateTimeParseException

interface CreditCardFraudService {
    fun creditCardFraudDetection(file: String, amount: String): Set<String>
}

@Service
class CreditCardFraudServiceImpl(
    private val transactionService: TransactionService,
    private val fraudService: FraudService
) : CreditCardFraudService {
    override fun creditCardFraudDetection(file: String, amount: String): Set<String> {
        val threshold = BigDecimal(amount)
        var transactions = emptyList<Transaction>()
        try {
            transactions = transactionService.readTransactionsFromFile(file)
        } catch (e: DateTimeParseException) {
            throw Exception("Incorrect datetime format in the sample file")
        } catch (e: NumberFormatException) {
            throw Exception("Threshold amount is in incorrect format in the sample file")
        } catch (e: IOException) {
            throw Exception("Incorrect path. File not found")
        }
        return fraudService.findFraudAccounts(transactions, threshold)
    }
}