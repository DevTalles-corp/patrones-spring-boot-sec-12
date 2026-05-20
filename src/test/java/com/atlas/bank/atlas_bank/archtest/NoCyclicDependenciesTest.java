package com.atlas.bank.atlas_bank.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(
        packages = "com.atlas.bank.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class NoCyclicDependenciesTest {

    @ArchTest
    static final ArchRule domain_modules_should_be_free_of_cycles =
            slices()
                    .matching("com.atlas.bank.atlas_bank.domain.model.(*)..")
                    .should().beFreeOfCycles()
                    .because("Los módulos del dominio (account, transaction, customer) " +
                            "no pueden depender circularmente");

    @ArchTest
    static final ArchRule layers_should_be_free_of_cycles =
            slices()
                    .matching("com.atlas.bank.atlas_bank.(*)..")
                    .should().beFreeOfCycles()
                    .because("domain, application e infrastructure no pueden tener dependencias circulares");

}










