package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.domain.model.account.Account;

public interface CreateAccountUseCase {
    Account create(CreateAccountCommand command);
}
