package com.atlas.bank.atlas_bank.domain.model.account;

import com.atlas.bank.atlas_bank.domain.event.AccountClosedEvent;
import com.atlas.bank.atlas_bank.domain.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.domain.exception.InsufficientFundsException;
import com.atlas.bank.atlas_bank.domain.model.shared.Currency;
import com.atlas.bank.atlas_bank.domain.model.shared.Email;
import com.atlas.bank.atlas_bank.domain.model.shared.Money;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Account {

    @EqualsAndHashCode.Include
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Email email;
    private AccountType type;
    private Money balance;
    private AccountStatus status;
    private LocalDateTime createdAt;
    private Long customerId;

    @Builder.Default
    private final List<Object> domainEvents = new ArrayList<>();

    public void close() {
        if (status != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(id, status.name());
        }
        if (!balance.equals(Money.zero(balance.getCurrency()))) {
            throw new IllegalStateException("No se puede cerrar una cuenta con saldo");
        }
        status = AccountStatus.CLOSED;
        domainEvents.add(new AccountClosedEvent(id, accountNumber, ownerName));
    }

    public List<Object> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void deposit(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("El monto a depositar no puede ser negativo");
        }
        balance = balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (amount.isNegative()) {
            throw new IllegalArgumentException("El monto a retirar no puede ser negativo");
        }
        if (balance.isLessThan(amount)) {
            throw new InsufficientFundsException(id, balance.getAmount(), amount.getAmount());
        }
        balance = balance.subtract(amount);
    }

    public void initDefaults() {
        if (status == null) status = AccountStatus.ACTIVE;
        if (balance == null) balance = Money.zero(Currency.ARS);
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}