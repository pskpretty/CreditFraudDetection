package com.creditcardFraudAlgorithm.controller

import com.creditcardFraudAlgorithm.model.Transaction
import com.creditcardFraudAlgorithm.service.CreditCardFraudService
import com.creditcardFraudAlgorithm.service.FraudService
import com.creditcardFraudAlgorithm.service.TransactionService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.math.BigDecimal
import java.time.format.DateTimeParseException

@RestController
@RequestMapping("/api")
class FraudController(
    private val creditCardFraudService: CreditCardFraudService
) {

    @PostMapping("/uploadCsv")
    fun uploadCsv(
        @RequestParam("file") file: String,
        @RequestParam("amount") threshold: String
    ): Set<String> {
        return creditCardFraudService.creditCardFraudDetection(file, threshold)
    }
}