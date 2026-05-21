package com.atlas.bank.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyDomainTest {


    @Test
    @DisplayName("Debe sumar dos montos en la misma moneda")
    void shouldAddSameCurrency() {
        Money a = Money.of(new BigDecimal("100"), Currency.ARS);
        Money b = Money.of(new BigDecimal("250.50"), Currency.ARS);

        Money result = a.add(b);

        assertEquals(Money.of(new BigDecimal("350.50"), Currency.ARS), result);
    }

    @Test
    @DisplayName("Debe rechazar operación entre monedas distintas")
    void shouldRejectDifferentCurrencies() {
        Money ars = Money.of(new BigDecimal("100"), Currency.ARS);
        Money usd = Money.of(new BigDecimal("100"), Currency.USD);

        assertThrows(
                IllegalArgumentException.class, () ->
                        ars.add(usd)
        );
    }

    @Test
    @DisplayName("Debe detectar monto negativo")
    void shouldDetectNegativeAmount() {
        Money negative = Money.of(new BigDecimal("-50"), Currency.ARS);

        assertTrue(negative.isNegative());
    }

    @Test
    @DisplayName("Debe comparar montos correctamente")
    void shouldCompareAmounts() {
        Money hundred = Money.of(new BigDecimal("100"), Currency.ARS);
        Money fifty = Money.of(new BigDecimal("50"), Currency.ARS);

        assertTrue(hundred.isGreaterThan(fifty));
        assertTrue(fifty.isLessThan(hundred));
    }

    @Test
    @DisplayName("Money.zero debe tener monto cero")
    void shouldCreateZeroMoney() {
        Money zero = Money.zero(Currency.ARS);

        assertEquals(new BigDecimal("0.00"), zero.getAmount());
        assertFalse(zero.isNegative());
    }

}














