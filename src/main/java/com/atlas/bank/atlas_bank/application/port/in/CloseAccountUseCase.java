package com.atlas.bank.atlas_bank.application.port.in;

import com.atlas.bank.atlas_bank.application.command.CloseAccountCommand;
import com.atlas.bank.atlas_bank.domain.model.account.Account;

public interface CloseAccountUseCase {
    Account close(CloseAccountCommand command);
}
