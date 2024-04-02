package dac.bantads.enums;

public enum AccountApprovalStatus {
    PENDING_APPROVAL("PENDING_APPROVAL"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    private String value;

    AccountApprovalStatus(String value) {
        this.value = value;
    }
}




