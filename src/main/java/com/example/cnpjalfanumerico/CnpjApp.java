package com.example.cnpjalfanumerico;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CnpjApp {

    public static void main(String[] args) {
        String base = CnpjGenerator.generateRandomBaseCnpj();
        log.info("Generated base CNPJ: {}", base);

        String dv = CnpjDVCalculator.calculateCheckDigits(base);
        log.info("Calculated DVs: {}", dv);

        String fullCnpj = base + dv;
        log.info("Complete CNPJ (base + DVs): {}", fullCnpj);

        boolean isValid = CnpjDVCalculator.validateCompleteCnpj(fullCnpj);
        log.info("Is the generated CNPJ valid? {}", isValid);

        CnpjDVCalculator.printConversionTable();

    }
}
