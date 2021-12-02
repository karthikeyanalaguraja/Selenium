package core.utilities.objects;

import core.utilities.enums.TransferTypes;
import core.utilities.enums.TransferUntil;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.cloudinary.json.JSONObject;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Random;

public class Transfer {

    public String accountNumber;
    public String accountNickname;
    public String routingAndTransit;
    public String transferLimit;
    public String description;
    public String amount;
    public String principalAmt;
    public String interestAmt;
    public String amountPrevious;
    public String date;
    public String untilDate;
    public String untilNumberOfTransfers;
    public String untilAmount;
    public String finalTransferAmount;
    public String finalTransferAmountUpdated;
    public String fromAccount;
    public String toAccount;
    public String frequency;
    public String period;
    public String transferId;
    public String transferIdPending;
    public int days;
    public TransferTypes transferType;
    public TransferUntil transferUntil;
    public String toAmount;
    public String grandTotal;
    public String submitDate;
    public WebElement fromAccountDropdown;
    public WebElement toAccountDropdown;
    public boolean partical;
    public boolean mfa;
    public boolean additionalAuth;
    public String enterTransferLimit;
    public HashMap<String, String> transferhmap = new HashMap<String, String>();
    public HashMap<String,String> periodTypeApiToUI = new HashMap<>();
    public HashMap<String,String> frequencyTypeApiToUI = new HashMap<>();
    public boolean productionTest;

    public void setMapValues() {

        String periodTypeKeys[] = {"ONCE","WEEKLY", "EVERYTWOWEEKS", "MONTHLY", "EVERYTWOMONTHS", "QUARTERLY", "TWICEAYEAR", "YEARLY"};
        String periodTypeValues[] = {"Once","Weekly", "Every Two Weeks", "Monthly", "Every Two Months", "Quarterly", "Twice a Year", "Yearly"};
        for(int i=0; i < periodTypeKeys.length; i++) {
            periodTypeApiToUI.put(periodTypeKeys[i], periodTypeValues[i]);
        }

        String frequencyTypeKeys[] = {"ONCE","FIXEDRECURRING", "VARIABLERECURRING", "OCCASIONAL"};
        String frequencyTypeValues[] = {"One-Time","Fixed Recurring", "Variable Recurring", "Occasional"};
        for(int i=0; i < frequencyTypeKeys.length; i++) {
            frequencyTypeApiToUI.put(frequencyTypeKeys[i], frequencyTypeValues[i]);
        }
    }
    public Transfer(){
        super();
        setMapValues();

    }

    public Transfer(JSONObject jsonTransfer) {
        super();
        setMapValues();
        // These fields should exist for all transfers
        fromAccount = String.valueOf(jsonTransfer.get("srcAccDescription"));
        toAccount = String.valueOf(jsonTransfer.get("destAccDescription"));
        date = String.valueOf(jsonTransfer.get("dateScheduled"));
        String frequencyAPI = String.valueOf(jsonTransfer.get("frequency"));
        frequency = frequencyTypeApiToUI.get(frequencyAPI);
        String periodAPI = String.valueOf(jsonTransfer.get("period"));
        period = periodTypeApiToUI.get(periodAPI);

        // amount values will be null when not returned from the API
        amount = String.valueOf(jsonTransfer.get("amount"));
        principalAmt = String.valueOf((jsonTransfer.get("principalAmt")));
        interestAmt = String.valueOf(jsonTransfer.get("interestAmt"));

        if(amount.equals("null")) {
            amount = null;
        }
        if(principalAmt.equals("null")) {
            principalAmt = null;
        }
        if(interestAmt.equals("null")) {
            interestAmt = null;
        }
        getUntilTypeFromTransferAPI(jsonTransfer);

        Allure.step(String.format("Creating transfer object date:%s frequency:%s, period:%s, transferUntil:%s",date,frequency,period,transferUntil));
        System.out.println("frequency="+frequency);
        System.out.println("period="+period);
        System.out.println("transferUntil="+transferUntil);
        System.out.println("amount="+amount);
        System.out.println("principalAmt="+principalAmt);
        System.out.println("inserestAmt="+interestAmt);

    }

    public void getUntilTypeFromTransferAPI(JSONObject jsonTransfer){
        JSONObject until  = jsonTransfer.getJSONObject("until");
        String untilTypeString = String.valueOf(until.get("type"));
        if(untilTypeString != null) {
            if(untilTypeString.equals("DATE")){
                transferUntil =  TransferUntil.SpecificDate;
                untilDate = until.getString("untilDate");
            } else if(untilTypeString.equals("CANCEL")){
                transferUntil =  TransferUntil.ICancel;
            } else if(untilTypeString.equals("AMOUNT")){
                transferUntil =  TransferUntil.TotalAmountTransferred;
                untilAmount = String.valueOf(until.get("untilAmount"));
            } else if(untilTypeString.equals("COUNT")){
                transferUntil =  TransferUntil.NumberOfTransfers;
                untilNumberOfTransfers = String.valueOf(until.get("untilCount"));
            }
        }
    }

    public void getFrequencyFromTransferAPI(JSONObject jsonTransfer) {
        String originalFrequency = String.valueOf(jsonTransfer.get("frequency"));
        frequency = frequencyTypeApiToUI.get(originalFrequency);

    }

    public void getPeriodFromTransferAPI(JSONObject jsonTransfer) {
        String originalPeriod = String.valueOf(jsonTransfer.get("period"));
        period = periodTypeApiToUI.get(originalPeriod);
    }


    public String addDate(int date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (date == 0) {
            return LocalDate.now().format(df);
        } else {
            return LocalDate.now().plusDays(date).format(df);
        }
    }

    /**
     *  getDescription() method contains the current date rather than the transfer date.
     * However it can't be fixed easily because it doesn't have access to the transfer object
     **/
    public String getDescription(String type, String Frequency, String Period) {
        Transfer transfer = new Transfer();
        String date = transfer.addDate(0);
        Random r = new Random();
        int n = r.nextInt(50) + 1;
        String description = type + "-Created:" + date + "-" + Frequency + "-" + Period + "-" + "-" + n;
        return description;
    }

    /**
     * getDescription() method contains the current date rather than the transfer date.
     * However it can't be fixed easily because it doesn't have access to the transfer object
     **/
    public String getDescription(String type, String Frequency, String Period, String UntilType) {
        Transfer transfer = new Transfer();
        String date = transfer.addDate(0);
        Random r = new Random();
        int n = r.nextInt(50) + 1;
        String description = type + "-Created:" + date + "-" + Frequency + "-" + Period + "-" +n+ "-" + UntilType;
        return description;
    }

    public String getAmountDecimal() {
        Random r = new Random();
        String amount = String.valueOf(r.nextInt(50 + 1) / 10.0);
        double tempAmount = Double.parseDouble(amount);
        while (tempAmount <=1.0) {
            amount = String.valueOf(r.nextInt(50 + 1) / 10.0);
            tempAmount = Double.parseDouble(amount);
        }
        String finalAmount = String.format("%.2f", tempAmount);
        return finalAmount;
    }

    public String getAmount() {
        Random r = new Random();
        String amount = String.valueOf(r.nextInt(25) + 1);
        return amount;
    }
    
    public String enterAmount(String amount) {
        double newAmountT = Double.parseDouble(amount) *10 ;
        String newAmount = String.valueOf(newAmountT);
        System.out.println("printing newAmount :" + newAmount);
        return newAmount;
    }

    // Fixed bug in getRandomAccount method
    public int getRandomAccount(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min)) + min;
    }

    public String findDay( int date ) {
        if (date == 0) {
            LocalDate localDate = LocalDate.now();
            return localDate.getDayOfWeek().name();
        } else {
            LocalDate localDate = LocalDate.now().plusDays(date);
            return localDate.getDayOfWeek().name();
        }
    }

    public String getRandomRecurringPeriod(){
        String periodTypes[] = {"Weekly", "Every Two Weeks", "Monthly", "Every Two Months", "Quarterly", "Twice a Year", "Yearly"};
        Random r = new Random();
        int n = r.nextInt(periodTypes.length);
        return periodTypes[n];
    }

    public String getRandomAccountNumber() {
        long timeSeed = System.nanoTime();
        double randSeed = Math.random() * 1000;
        long midSeed = (long) (timeSeed * randSeed);
        String s = midSeed + "";
        String subStr = s.substring(0, 9);
        return subStr;
    }

    public String getRandomAccountNickname() {
        Random rand = new Random();
        int n = rand.nextInt(10000000) + 100;
        String randomNickname = "Nickname: " + n;
        return randomNickname;
    }

    public String getRandomRoutingNumber() {
        String routingNumbers[] = {"123123123", "053000196", "272477694", "053208037"};
        System.out.println(routingNumbers.length);
        Random rand = new Random();
        int n = rand.nextInt(routingNumbers.length);
        return routingNumbers[n];

    }

    public String getRandomBankName() {
        String bankName[] = {"Foo Bank", "Bank of America", "Zeal Credit Union", "Capital Bank"};
        System.out.println(bankName.length);
        Random rand = new Random();
        int n = rand.nextInt(bankName.length);
        return bankName[n];
    }


    public void getHashMap() {

        transferhmap.put("amount1", getAmountDecimal());
        transferhmap.put("amount2", getAmountDecimal());
        transferhmap.put("amount3", getAmountDecimal());
        transferhmap.put("amount4", getAmountDecimal());
        transferhmap.put("frequency1","One-Time");
        transferhmap.put("frequency2","Fixed Recurring");
        transferhmap.put("frequency3","Variable Recurring");
        transferhmap.put("frequency4","Occasional");
        transferhmap.put("period1","Once");
        transferhmap.put("period2","Weekly");
        transferhmap.put("period3","Monthly");
        transferhmap.put("period4","Quarterly");
        transferhmap.put("description1",getDescription("T1-MultipleTransfer", transferhmap.get("frequency1"), transferhmap.get("period1")));
        transferhmap.put("description2",getDescription("T2-MultipleTransfer", transferhmap.get("frequency2"), transferhmap.get("period2")));
        transferhmap.put("description3",getDescription("T3-MultipleTransfer", transferhmap.get("frequency3"), transferhmap.get("period3")));
        transferhmap.put("description4",getDescription("T4-MultipleTransfer", transferhmap.get("frequency4"), transferhmap.get("period4")));
        transferhmap.put("date1", addDate(6));
        transferhmap.put("date2", addDate(6));
        transferhmap.put("date3", addDate(6));
        transferhmap.put("date4", addDate(6));

    }

}





