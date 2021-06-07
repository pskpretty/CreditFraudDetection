package com.creditcardFraudAlgorithm.service

import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class FraudServiceTest {
    val fraudService = FraudServiceImpl()

    @Test
    fun `should detect fraud from one transaction`() {
        TestFactory.createCardTransaction().apply {
            fraudService.findFraudAccounts(this, BigDecimal.valueOf(49.0)).apply {
                assertEquals(this.size, 1)
            }
        }
    }

    @Test
    fun `should not detect fraud from one transaction if threshold is higher`() {
        TestFactory.createCardTransaction().apply {
            fraudService.findFraudAccounts(this, BigDecimal.valueOf(51.0)).apply {
                assertEquals(this.size, 0)
        }
    }
    }
}