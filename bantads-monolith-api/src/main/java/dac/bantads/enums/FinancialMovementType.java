package dac.bantads.enums;

public enum FinancialMovementType {
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL"),
    TRANSFER("TRANSFER");

    private String value;

    FinancialMovementType(String value) {
        this.value = value;
    }
}




