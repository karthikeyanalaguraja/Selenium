package core.utilities.enums;

public enum TransferFrequency {
    ONCE("One-Time"),
    FIXEDRECURRING("Fixed Recurring"),
    VARIABLERECURRING("Variable Recurring"),
    OCCASIONAL("Occasional");

    String transferFrequency;

    TransferFrequency(String value) {
        transferFrequency = value;
    }

    public String getTransferFrequency() {
        return transferFrequency;
    }
}
