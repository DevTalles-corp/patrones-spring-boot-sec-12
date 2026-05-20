package com.atlas.bank.atlas_bank.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
        packages = "com.atlas.bank.atlas_bank",
        importOptions = ImportOption.DoNotIncludeTests.class
)
public class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domain_should_not_depend_on_infrastructure =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .because("El dominio es el núcleo puro — no puede conocer la existencia " +
                            "de adaptadores ni configuraciones");

//    @Test
//    void domain_should_not_depend_on_infrastructure2() {
//        noClasses()
//                .that().resideInAPackage("..domain..")
//                .should().dependOnClassesThat()
//                .resideInAPackage("..infrastructure..")
//                .because("El dominio es el núcleo puro — no puede conocer la existencia " +
//                        "de adaptadores ni configuraciones");
//
//    }

    @ArchTest
    static final ArchRule domain_should_not_depend_on_application =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..application..")
                    .because("El dominio es autosuficiente — no conoce use cases, commands ni services");

    // ── Regla 3: Application no se saltea los puertos ──

    @ArchTest
    static final ArchRule application_should_not_depend_on_infrastructure =
            noClasses()
                    .that().resideInAPackage("..application..")
                    .should().dependOnClassesThat()
                    .resideInAPackage("..infrastructure..")
                    .because("La capa de aplicación usa puertos (interfaces), nunca implementaciones concretas");


    // ── Regla 4: El dominio es Java puro — sin Spring ──

    @ArchTest
    static final ArchRule domain_should_not_depend_on_spring =
            noClasses()
                    .that().resideInAPackage("..domain..")
                    .should().dependOnClassesThat()
                    .resideInAnyPackage("org.springframework..")
                    .because("El dominio debe ser Java puro — testearlo no requiere Spring context");

}















