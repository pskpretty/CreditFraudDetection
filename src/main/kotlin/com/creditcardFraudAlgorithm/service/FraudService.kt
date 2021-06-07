package com.creditcardFraudAlgorithm.service

import com.creditcardFraudAlgorithm.model.SlidingTransactionWindow
import com.creditcardFraudAlgorithm.model.Transaction
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

interface FraudService {
    fun findFraudAccounts(transactionList: List<Transaction>, thresholdAmount: BigDecimal): Set<String>
}

@Service
class FraudServiceImpl : FraudService {
    private var cardMap: MutableMap<String, BigDecimal> = mutableMapOf()

    private var fraudCardNumbers = mutableSetOf<String>()

    /**
     * Detect fraud transaction card number from the list
     * <p/>
     *
     * @param transactionList list of {@link Transaction}
     * @param thresholdAmount cut off amount
     */
    override fun findFraudAccounts(transactionList: List<Transaction>, thresholdAmount: BigDecimal): Set<String> {
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
            //If a card is already marked as fraudulent then we can skip it from processing again in next 24 hour block
            when (fraudCardNumbers.contains(transaction.hashCardNumber)) {
                true -> i++
                else -> checkFraudInSlidingWindow(slidingTransactionWindow, thresholdAmount)
            }
        }
        return fraudCardNumbers
    }

    /**
     * Sliding window frame to identify cards within 24 hours
     * <p/>
     *
     * @param transactionList list of {@link SlidingTransactionWindow}
     * @param thresholdAmount cut off amount
     */
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

    /**
     * Utility function to add card and sum within 24 hour frame
     * <p/>
     *
     * @param cardNumber String
     * @param transactionAmount per transaction amount
     */
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