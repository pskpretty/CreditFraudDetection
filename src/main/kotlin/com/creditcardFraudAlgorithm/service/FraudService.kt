package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.SlidingTransactionWindow
import com.creditcardFraudAlgorithm.model.Transaction
import java.math.BigDecimal
import java.time.LocalDateTime

class FraudService {
    private var cardMap: MutableMap<String, BigDecimal> = mutableMapOf()

    private var fraudCardNumbers = mutableSetOf<String>()
    fun detectFraud(transactionList: List<Transaction>, thresholdAmount: BigDecimal): Set<String> {
        var i = 0
        val l = transactionList.size
        transactionList.forEach { transaction ->
            val start: LocalDateTime = transaction.timestamp
            val end: LocalDateTime = transaction.timestamp.plusHours(24)
            val slidingTransactionWindow = SlidingTransactionWindow(
                transactionList.subList(i, l),
                start,
                end
            )
            when (fraudCardNumbers.contains(transaction.hashCardNumber)) {
                true -> i++
                else -> checkFraudInSlidingWindow(slidingTransactionWindow, thresholdAmount)
            }
        }
        return fraudCardNumbers
    }

    private fun checkFraudInSlidingWindow(
        slidingTransactionWindow: SlidingTransactionWindow, thresholdAmount: BigDecimal
    ) {
        slidingTransactionWindow.transactionSubList.filter {
            it.isWithinTimeFrame(slidingTransactionWindow.start, slidingTransactionWindow.end)
        }.forEach {
            addCardAndAmountToMap(it.hashCardNumber, it.amount)
        }
        cardMap.filter { it.value > thresholdAmount && !fraudCardNumbers.contains(it.key) }
            .forEach { fraudCardNumbers.add(it.key) }
        cardMap = mutableMapOf()
    }


    private fun addCardAndAmountToMap(cardNumber: String, transactionAmount: BigDecimal) {
        if (cardMap.containsKey(cardNumber)) {
            val oldTotalAmount = cardMap[cardNumber]
            val newTotalAmount = transactionAmount + oldTotalAmount!!
            cardMap[cardNumber] = newTotalAmount
        } else {
            cardMap[cardNumber] = transactionAmount
        }
    }

    private fun Transaction.isWithinTimeFrame(start: LocalDateTime, end: LocalDateTime): Boolean {
        return (this.timestamp.isAfter(start) || this.timestamp == start) &&
                (this.timestamp.isBefore(end) || this.timestamp == end)
    }
}