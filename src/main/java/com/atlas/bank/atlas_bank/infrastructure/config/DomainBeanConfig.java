package com.atlas.bank.atlas_bank.infrastructure.config;

import com.atlas.bank.atlas_bank.application.validation.FraudValidator;
import com.atlas.bank.atlas_bank.domain.service.TransferDomainService;
import com.atlas.bank.atlas_bank.domain.strategy.fee.*;
import com.atlas.bank.atlas_bank.domain.validation.*;
import com.atlas.bank.atlas_bank.application.port.out.FraudCheckPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DomainBeanConfig {

    @Bean
    public TransferDomainService transferDomainService() {
        return new TransferDomainService();
    }

    @Bean @Order(1)
    public SavingsFeeCalculator savingsFeeCalculator() {
        return new SavingsFeeCalculator();
    }

    @Bean @Order(2)
    public CheckingFeeCalculator checkingFeeCalculator() {
        return new CheckingFeeCalculator();
    }

    @Bean @Order(3)
    public PremiumFeeCalculator premiumFeeCalculator() {
        return new PremiumFeeCalculator();
    }

    @Bean
    public DefaultFeeCalculator defaultFeeCalculator() {
        return new DefaultFeeCalculator();
    }

    @Bean @Order(1)
    public AccountStatusValidator accountStatusValidator() {
        return new AccountStatusValidator();
    }

    @Bean @Order(2)
    public SufficientFundsValidator sufficientFundsValidator() {
        return new SufficientFundsValidator();
    }

    @Bean @Order(3)
    public FraudValidator fraudValidator(FraudCheckPort fraudCheckPort) {
        return new FraudValidator(fraudCheckPort);
    }
}