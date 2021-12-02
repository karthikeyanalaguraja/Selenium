package core.utilities.enums;

public enum TransferTypes {
    SingleTransfer("Single Transfer"),
    NewLoanTransfer("New Loan Transfer"),
    FromOnetoMany("From One(1) to Many"),
    FromManytoOne("From Many to One(1)"),
    FromOnetoOne("From One(1) to One(1)"),
    MultipleTransfer("Multiple Transfer");

    String transferType;

    TransferTypes(String transferType) {
        this.transferType = transferType;
    };

    public String getTransferType() {
        return this.transferType;
    }


}
