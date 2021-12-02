package core.utilities.objects;


import core.utilities.enums.Institutions;

public class UserTypes {

    private UserTypes() {}

    //TODO: Convert institution IDs from meaningless number to its actual FIID.  For example, FOO should be referred to as "FOO" and not "1".  Also, Pinnacle should be "PFPNTN" instead of "3".
    static String apitureId = Institutions.APITURE.getInstitutionId();
    static String pinnacleId = Institutions.PFPNTN.getInstitutionId();
    static String fooId = Institutions.FOO.getInstitutionId();
    static String ztboiId = Institutions.ZTBOI.getInstitutionId();
    static String FXIMId = "4";
    static String fooProdRetailId = Institutions.FOOPRODUCTION.getInstitutionId();


    //TODO: abstract types of users to be distinct from the institutions to which they belong.  In other words, if a test only needs a Retail user, then it should not matter from which institution.
    //TODO: allow for types of users to be "ANY" which should allow for a wider sample size of users for certain tests to utilize
    public static UserType FXIM = new UserType(FXIMId, "FXIM");
    public static UserType FximDashboard = new UserType(pinnacleId, "FximDashboard");
    public static UserType DisposableTest = new UserType(apitureId, "Disposable");
    public static UserType DisposableTestComm = new UserType(apitureId, "Disposable-Comm");
    public static UserType ApitureRetail = new UserType(apitureId, "Retail");
    public static UserType SecurityTest = new UserType(apitureId, "Retail");
    public static UserType PinnacleCommercial = new UserType(pinnacleId, "PFPNTNCommercial");
    public static UserType PinnacleRetail = new UserType(pinnacleId, "PFPNTNRetail");
    public static UserType PasswordReset = new UserType(apitureId, "Password");
    public static UserType RetailAccountTest = new UserType(apitureId, "AccountTests");
    public static UserType RetailWires = new UserType(apitureId, "RetailWires");
    public static UserType CommercialAccountTest = new UserType(apitureId, "CommercialAccountTests");
    public static UserType CommercialBillpay = new UserType(apitureId, "CommercialBillpay");
    public static UserType TransferOneAccountTest = new UserType(apitureId, "TransferOneAccount");
    public static UserType TransferTwoAccountTest = new UserType(apitureId, "TransferTwoAccount");
    public static UserType GeneralTransferTest = new UserType(apitureId,"GeneralTransferTest");
    public static UserType RetailAchTest = new UserType(apitureId,"RetailAchTest");
    public static UserType FooCommercial = new UserType(fooId, "FooCommercial");
    public static UserType MaxFooCommercial = new UserType(fooId, "MaxCommercial");
    public static UserType FXIMCommercialUser = new UserType(fooId, "FXIMCommercial");
    public static UserType FooRetailKarthik = new UserType(fooId,"FooRetailKarthik");
    public static UserType FiservCards = new UserType(fooId,"FiservCards");
    public static UserType ShazamCards = new UserType(ztboiId,"ShazamCards");
    public static UserType FiservBillPay = new UserType(pinnacleId,"FiservBillPay");
    public static UserType FooProdRetail = new UserType(fooProdRetailId, "FooProdRetail");
    public static UserType FooAgent = new UserType(fooId, "Agent");
    public static UserType HomeTestRetail = new UserType(apitureId,"HomeTest-Retail");
    public static UserType SBTest = new UserType(apitureId,"SBTest");
    public static UserType SBSimpleTest = new UserType(apitureId,"SBSimpleTest");
    public static UserType StopCheckPayment = new UserType(apitureId, "StopCheckPayment");
    public static UserType RetailLowBal = new UserType(apitureId,"Retail-LowBal");
    public static UserType RetailNegative = new UserType(apitureId,"Retail-Negative");
    public static UserType RetailHomeTest = new UserType(apitureId,"RetailHomeTest");
    public static UserType CommercialCashManagement = new UserType(apitureId,"CashManagementTests");
    public static UserType HomeAccountSort = new UserType(apitureId,"Retail-AccSort");
    public static UserType CommercialAccountSort = new UserType(apitureId,"Comm-AccSort");
    public static UserType Tactical = new UserType(apitureId, "Tactical");
    public static UserType TacticalGeneralCredit = new UserType(apitureId,"Tactical-General-C");
    public static UserType TacticalGeneralDebit = new UserType(apitureId,"Tactical-General-D");
    public static UserType TacticalGeneralImport = new UserType(apitureId,"Tactical-General-I");
    public static UserType TacticalGeneralCM = new UserType(apitureId,"Tactical-General-C-M");
    public static UserType TacticalGeneralDM = new UserType(apitureId,"Tactical-General-D-M");
    public static UserType TacticalGeneralTax = new UserType(apitureId,"Tactical-General-T");
    public static UserType TacticalGeneralTaxSpecial = new UserType(apitureId,"Tactical-General-T-S");
    public static UserType TacticalFCAgentTax = new UserType(apitureId,"Tactical-FC-Agent-T");
    public static UserType TacticalAgentTax = new UserType(apitureId,"Tactical-Agent-T");
    public static UserType RetailTax = new UserType(apitureId,"RetailTaxTest");
    public static UserType TacticalTax = new UserType(apitureId, "Tactical-T");
    public static UserType TacticalSUCreateTax = new UserType(apitureId,"SubUser-Create-T");
    public static UserType TacticalSUApproveTax = new UserType(apitureId,"SubUser-Approve-T");
    public static UserType RetailMFA = new UserType(apitureId,"RetailMFA");
    public static UserType TacticalFCAgent = new UserType(apitureId,"Tactical-FC-Agent");
    public static UserType TacticalFCAgentR = new UserType(apitureId,"Tactical-FC-Agent-R");
    public static UserType TacticalFocusEarly = new UserType(apitureId,"Tactical-Focus-E");
    public static UserType TacticalFocusNormal = new UserType(apitureId,"Tactical-Focus-N");
    public static UserType TacticalFocusFloat = new UserType(apitureId,"Tactical-Focus-F");
    public static UserType MFAOnACH = new UserType(fooId, "MFA-OnACH");
    public static UserType TacticalAgent1 = new UserType(apitureId,"Tactical-Agent1");
    public static UserType TacticalAgent2 = new UserType(apitureId,"Tactical-Agent2");
    public static UserType TacticalSUCreate = new UserType(apitureId,"SubUser-Create");
    public static UserType TacticalSUApprove = new UserType(apitureId,"SubUser-Approve");
    public static UserType SubUserTest = new UserType(apitureId, "SubUserTest");
    public static UserType ZilkerTest = new UserType(apitureId, "Zilker");
    public static UserType IncorrectPass = new UserType(apitureId, "IncorrectPass");
    public static UserType ZilkerTestHome = new UserType(apitureId, "Zilker-Home");
    public static UserType OutsideAccounts = new UserType(apitureId, "OutsideAccounts");


    //These are the usertype for Production
    public static UserType Automation1 = new UserType(apitureId,"Automation1");
    public static UserType Automation2 = new UserType(apitureId,"Automation2");
    public static UserType Automation3 = new UserType(apitureId,"Automation3");
    public static UserType Automation4 = new UserType(apitureId,"Automation4");
    public static UserType Automation5 = new UserType(apitureId,"Automation5");
    public static UserType AutomationS1 = new UserType(apitureId,"AutomationS1");
    public static UserType AutomationS2 = new UserType(apitureId,"AutomationS2");
    public static UserType DisposableProd = new UserType(apitureId,"Disposable_PROD");

    //These are the usertype for SSO
    public static UserType MetavanteTest = new UserType(apitureId,"Metavante");
    public static UserType IpayTest = new UserType(apitureId,"Ipay");
    public static UserType EnsentaTest = new UserType(apitureId,"Ensenta");
    public static UserType OptriaistatementsTest = new UserType(apitureId,"Optriaistatements");
    public static UserType CountyservicesTest = new UserType(apitureId,"Countyservices");
    public static UserType SunGardTest = new UserType(apitureId,"SunGard_PF");
    public static UserType GoToMyCardTest = new UserType(apitureId,"gotomycard");
    public static UserType UOpenTest = new UserType(apitureId,"harlanduopen");
    public static UserType HighCottonTest = new UserType(apitureId,"high_cotton");
    public static UserType AcculynkTest = new UserType(apitureId,"acculynk");
    public static UserType MineralTreeTest = new UserType(apitureId,"MineralTree");
    public static UserType ACHAlertTest = new UserType(apitureId,"ACHAlert");
    public static UserType eStatementsTests = new UserType(apitureId,"eStatements");
    public static UserType InfoLinkPlusTests = new UserType(apitureId,"InfoLinkPlus");
    public static UserType MerdianLinkTests = new UserType(apitureId,"meridianlink");
    public static UserType PrintMailTests = new UserType(apitureId,"printmail");
    public static UserType GlobalViewPointTest = new UserType(apitureId,"global_viewpoint");
    public static UserType WexSSO = new UserType(pinnacleId, "wexSSO");
    public static UserType FidelityStatements = new UserType(apitureId, "Fidelitystatements");
    public static UserType SmallBizUI = new UserType(apitureId,"SmallBizUI");
    public static UserType FiservUI = new UserType(apitureId,"CFRBUI");
    public static UserType IPAYUI = new UserType(apitureId,"IPAYUI");
    public static UserType MetavanteUI = new UserType(apitureId,"MetavanteUI");

    //These are the usertype for VisualTesting
    public static UserType VisualHomePagination = new UserType(apitureId, "VisualHome-Page");
    public static UserType VisualHome = new UserType(apitureId, "VisualHome");
    public static UserType VisualRetail = new UserType(apitureId, "VisualRetail");
    public static UserType VisualAgent = new UserType(apitureId, "VisualAgent");
    public static UserType VisualCommACH = new UserType(apitureId, "VisualCommACH");
    public static UserType VisualMessages = new UserType(apitureId,"VisualMessages");
    public static UserType VisualAccount = new UserType(apitureId,"VisualAccounts");
    public static UserType VisualOutisdeAccount = new UserType(apitureId,"VisualOutAccounts");
    public static UserType VisualZilker = new UserType(apitureId,"VisualZilker");


}
