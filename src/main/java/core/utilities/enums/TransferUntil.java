package core.utilities.enums;

public enum TransferUntil {
    ICancel("I Cancel"),
    SpecificDate("Specific Date"),
    NumberOfTransfers("Number of Transfers"),
    TotalAmountTransferred("Total Amount Transferred");

    String transferUntil;

    TransferUntil(String transferUntil){
        this.transferUntil = transferUntil;
    }

    public String getTransferUntil(){
        return this.transferUntil;
    }

}
