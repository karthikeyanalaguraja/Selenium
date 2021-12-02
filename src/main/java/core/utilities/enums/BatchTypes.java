package core.utilities.enums;

public enum BatchTypes {
    PPDCredit("PPD Credit"),
    PPDDebit("PPD Debit"),
    CCDCredit("CCD Credit"),
    CCDDebit("CCD Debit"),
    CTXCredit("CTX Credit"),
    CTXDebit("CTX Debit"),
    ARCDebit("ARC Debit"),
    BOCDebit("BOC Debit"),
    TELDebit("TEL Debit"),
    RCKDebit("RCK Debit"),
    WEBDebit("WEB Debit"),
    NachaImport("NACHA Import"),
    Tax("EFTPS");

    String batchType;

    BatchTypes( String batchType) {
        this.batchType = batchType;
    };

    public String getBatchType() {
        return this.batchType;
    }


}
