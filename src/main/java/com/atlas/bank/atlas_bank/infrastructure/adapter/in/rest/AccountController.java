package com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest;

import com.atlas.bank.atlas_bank.application.command.CreateAccountCommand;
import com.atlas.bank.atlas_bank.application.facade.AccountDashboardFacade;
import com.atlas.bank.atlas_bank.application.port.in.CreateAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.GetAccountUseCase;
import com.atlas.bank.atlas_bank.application.port.in.ListAccountsUseCase;
import com.atlas.bank.atlas_bank.application.query.DashboardReadModel;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountMapper;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.AccountResponse;
import com.atlas.bank.atlas_bank.infrastructure.adapter.in.rest.dto.CreateAccountRequest;
import com.atlas.bank.atlas_bank.domain.model.account.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final ListAccountsUseCase listAccountsUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final AccountMapper accountMapper;
    private final AccountDashboardFacade dashboardFacade;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DashboardReadModel> getDashboard(@PathVariable Long id){
        return ResponseEntity.ok(dashboardFacade.getDashboard(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request){
        CreateAccountCommand command = CreateAccountCommand.builder()
                .accountNumber(request.getAccountNumber())
                .ownerName(request.getOwnerName())
                .email(request.getEmail())
                .type(request.getType())
                .balance(request.getBalance())
                .build();

        Account saved = createAccountUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        List<AccountResponse> responses = listAccountsUseCase.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(accountMapper.toResponse(getAccountUseCase.findById(id)));
    }
}








