package com.example.cnpjalfanumerico;

import lombok.extern.slf4j.Slf4j;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CnpjDVCalculator {

    public static final Map<Character, Integer> valueTable = createValueTable();

    private static Map<Character, Integer> createValueTable() {
        return IntStream.concat(
                IntStream.rangeClosed('0', '9'),
                IntStream.rangeClosed('A', 'Z')
        )
                .boxed()
                .collect(Collectors.toMap(
                        i -> (char) i.intValue(),
                        i -> Character.isDigit(i) ? i - '0' : i - 'A' + 17
                ));
    }

    private static int calculateCheckDigit(String cnpj, int[] weights) {
        int sum = IntStream.range(0, weights.length)
                .map(i -> {
                    char c = Character.toUpperCase(cnpj.charAt(i));
                    return valueTable.getOrDefault(c, 0) * weights[i];
                })
                .sum();
        int remainder = sum % 11;
        return (remainder == 0 || remainder == 1) ? 0 : (11 - remainder);
        }

    public static String calculateCheckDigits(String cnpj12) {
        if (cnpj12 == null || cnpj12.length() != 12) {
            throw new IllegalArgumentException("CNPJ must contain 12 characters.");
        }
        int[] weightsDV1 = {5,4,3,2,9,8,7,6,5,4,3,2};
        int dv1 = calculateCheckDigit(cnpj12, weightsDV1);

        String cnpj13 = cnpj12 + dv1;
        int[] weightsDV2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
        int dv2 = calculateCheckDigit(cnpj13, weightsDV2);

        return "" + dv1 + dv2;
        }


    public static boolean validateCompleteCnpj(String fullCnpj) {
        if (fullCnpj == null || fullCnpj.length() != 14) {
            log.warn("CNPJ invalid or null: {}", fullCnpj);
            return false;
        }

        String base = fullCnpj.substring(0, 12);
        String expectedDV = calculateCheckDigits(base);
        String actualDV = fullCnpj.substring(12);

        boolean isValid = expectedDV.equals(actualDV);
        log.info("Checking CNPJ: {} -> {}", fullCnpj, isValid ? "valid" : "invalid");
        return isValid;


    }

    public static void printConversionTable() {
        log.info("Alphanumeric Conversion Table:");
        valueTable.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(characterIntegerEntry -> {
                    String line = String.format(" %-3s -> %2d", "'" + characterIntegerEntry.getKey() + "'", characterIntegerEntry.getValue());
                    log.info(line);
                });
    }
}
