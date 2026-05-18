package com.atlas.bank.atlas_bank.application.service;

import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Primary
public class AuditableAccountService implements CreateAccountUseCase, ListAccountsUseCase, GetAccountUseCase {

    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;

    public AuditableAccountService(@Qualifier("accountService") CreateAccountUseCase createAccountUseCase,
                                   @Qualifier("accountService") ListAccountsUseCase listAccountsUseCase,
                                   @Qualifier("accountService") GetAccountUseCase getAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.listAccountsUseCase = listAccountsUseCase;
        this.getAccountUseCase = getAccountUseCase;
    }

    @PostConstruct
    public void init()
    {
        log.info("Clase real del accountService: {}", createAccountUseCase.getClass().getName());
        log.info("Clase real del accountService: {}", listAccountsUseCase.getClass().getName());
        log.info("Clase real del accountService: {}", getAccountUseCase.getClass().getName());
    }

    @Override
    public Account create(CreateAccountCommand command) {
        log.info("Creando cuenta — número: {}, titular: {}",
                command.accountNumber(), command.ownerName());

        Account created = createAccountUseCase.create(command);

        log.info("Cuenta creada exitosamente — ID: {}", created.getId());

        return created;
    }

    @Override
    public List<Account> findAll() {
        return listAccountsUseCase.findAll();
    }

    @Override
    public Account findById(Long id) {
        return getAccountUseCase.findById(id);
    }
}
