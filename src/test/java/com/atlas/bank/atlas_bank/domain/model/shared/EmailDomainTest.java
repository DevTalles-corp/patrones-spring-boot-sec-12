package com.atlas.bank.atlas_bank.domain.model.shared;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailDomainTest {

    @Test
    @DisplayName("Debe crear email con formato válido")
    void shouldCreateValidEmail() {
        Email email = Email.of("maria@atlas.bank");

        assertEquals("maria@atlas.bank", email.getValue());
    }

    @Test
    @DisplayName("Debe normalizar a minúsculas")
    void shouldNormalizeToLowerCase() {
        Email email = Email.of("MARIA@Atlas.Bank");

        assertEquals("maria@atlas.bank", email.getValue());
    }

    @Test
    @DisplayName("Debe rechazar email con formato inválido")
    void shouldRejectInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                Email.of("no-es-un-email")
        );
    }

    @Test
    @DisplayName("Debe rechazar email nulo")
    void shouldRejectNullEmail() {
        assertThrows(IllegalArgumentException.class, () ->
                Email.of(null)
        );
    }

}












