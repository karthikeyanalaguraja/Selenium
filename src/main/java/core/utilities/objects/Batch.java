package core.utilities.objects;

import core.utilities.enums.BatchTypes;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Batch {

    public BatchTypes batchTypeEnum;
    public String userName;
    public String batchType;
    public String trackingNumber;
    public String batchNameTmp;
    public String batchName;
    public String frequency;
    public String period;
    public String date;
    public String validateDate;
    public int days;
    public int importBatchSize;
    public WebElement settlementAccountDropdown;
    public String settlementAccount;
    public String itemRoutingNumber;
    public String itemAccountNumber;
    public String itemCustomerName;
    public String itemCustomerId;
    public String itemAmount;
    public String itemOnHold;
    public String numOfItem;
    public String addenda;
    public String totalAmount;
    public String enterTotalAmount;
    public String creditAmount;
    public String debitAmount;
    public String holdAmount;
    public String settlementMessage;
    public boolean approve;
    public boolean sameDay;
    public boolean settlementType;
    public boolean remittanceOnly;
    public boolean credit;
    public boolean debit;
    public boolean balancedBatch;
    public boolean balanced;
    public boolean importARC;
    public boolean importBOC;
    public boolean importCTX;
    public boolean importPOP;
    public boolean importCIE;
    public boolean importItems;
    public boolean retail;
    public boolean commercial;
    public boolean commercialApprove;
    public boolean commercialSave;
    public boolean commercialCopy;
    public boolean commercialRequestApprove;
    public String userId;
    public String focusId;
    public String agentId;
    public String accessId;
    public String copyBatchId;
    public String copyBatchIdhistory;
    public boolean newBatch;
    public boolean otherFilter;
    public boolean requestApproval;
    public boolean approveOnlyCustomer;
    public String firstSecurityAnswer;
    public boolean nachaImport;
    public String taxDueDate;
    public String taxType;
    public String taxSubType;
    public String ficaAmount;
    public String enterFicaAmount;
    public String medicareAmount;
    public String enterMedicareAmount;
    public String holdingAmount;
    public String enterHoldingAmount;
    public boolean eftps;
    public boolean onlyEftpsAmount;
    public boolean mfa;
    public boolean additionalAuth;
    public List<String> trackingNumbersComm = new ArrayList<>();
    public List<String> trackingNumbersCopy = new ArrayList<>();
    public List<String> batchTypeComm = new ArrayList<>();
    public List<String> batchCreditComm = new ArrayList<>();
    public List<String> batchDebitComm = new ArrayList<>();
    public boolean productionTest;


    public String getBatchName() {
        Random r = new Random();
        int n = r.nextInt(50) + 1;
        String batchName = batchNameTmp + "-" + n;
        return batchName;
    }

    public String getRandomFrequency() {
        String frequency[] = {"Fixed Recurring Payment", "Variable Recurring Payment", "Occasional Payment"};
        Random rand = new Random();
        int n = rand.nextInt(frequency.length);
        return frequency[n];
    }

    public String getRandomPeriod() {
        String period[] = {"Daily", "Weekly", "Bi-Weekly", "Semi-Monthly", "Monthly", "Monthly-First Business Day", "Monthly-Last Business Day", "Bi-Monthly", "Quarterly", "Semi-Yearly", "Yearly"};
        Random rand = new Random();
        int n = rand.nextInt(period.length);
        return period[n];
    }

    public String getRandomPeriodNew() {
        String period[] = {"Daily", "Weekly", "Bi-Weekly", "Semi-Monthly", "Monthly", "Monthly-First", "Monthly-Last", "Bi-Monthly", "Quarterly", "Semi-Yearly", "Yearly"};
        Random rand = new Random();
        int n = rand.nextInt(period.length);
        return period[n];
    }

    public String getTaxDueDate(){
        String date[] = {"12/21/2020", "12/14/2020", "12/07/2020","12/06/2021", "12/13/2021","12/20/2021"};
        Random rand = new Random();
        int n = rand.nextInt(date.length);
        return date[n];
    }

    public String addDate( int date ) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (date == 0) {
            LocalDate localDate = LocalDate.now();
            return dtf.format(localDate);
        } else {
            LocalDate localDate = LocalDate.now().plusDays(date);
            return dtf.format(localDate);
        }
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

    public String getSameDayDate( Batch batch ) {
        String day = findDay(batch.days);
        if (day == "SATURDAY") {
            if (batch.credit) {
                day = addDate(batch.days + 2);
            } else if (batch.debit) {
                day = addDate(batch.days);
            }

        } else if (day == "SUNDAY") {
            if (batch.credit) {
                day = addDate(batch.days + 1);
            } else if (batch.debit) {
                day = addDate(batch.days);
            }
        } else  {
            day = addDate(batch.days);
        }
        return day;
    }

    public boolean checkIfHolliday(){
        String dateFormat = "yyyy-MM-dd";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String today = sdf.format(cal.getTime());
        //System.out.println(today);
        ArrayList<String> holidays = new ArrayList<>();
        holidays.add("2020-10-11");
        holidays.add("2021-11-11");
        holidays.add("2021-11-25");
        holidays.add("2021-12-25");
    

        for(int i = 0; i < holidays.size(); i++){
            if(holidays.get(i).equals(today)){
                return true;
            }
        }
        return false;
    }

    public boolean checkIfEffectiveDateIsHoliday(String tempDate){
        String effectiveDate = tempDate;
        //System.out.println(effectiveDate);
        ArrayList<String> holidays = new ArrayList<>();
        holidays.add("2020-12-25");
        holidays.add("2021-01-01");
        holidays.add("2021-01-18");
        holidays.add("2021-02-15");
        holidays.add("2021-05-31");
        holidays.add("2021-07-05");
        holidays.add("2021-09-06");
        holidays.add("2020-10-11");
        holidays.add("2021-11-11");
        holidays.add("2021-11-25");
        holidays.add("2021-12-25");

        for(int i = 0; i < holidays.size(); i++){
            if(holidays.get(i).equals(effectiveDate)){
                return true;
            }
        }
        return false;
    }


    public boolean validDateToRunBatch(){
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        //System.out.println(Calendar.SUNDAY);
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        //System.out.println(c.get(Calendar.DAY_OF_MONTH));
        if((checkIfHolliday() == true) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
            return false;
        }
        return true;
    }

    public boolean validDateToRunBatch_Ach(){
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        //System.out.println(Calendar.SUNDAY);
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        //System.out.println(c.get(Calendar.DAY_OF_MONTH));
        if((checkIfHolliday() == true) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
            return false;
        }
        return true;
    }

    public boolean validateDateHoliday( String tempDate){
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        //System.out.println(Calendar.SUNDAY);
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        //System.out.println(c.get(Calendar.DAY_OF_MONTH));
        if((checkIfHolliday() == true) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)){
            return false;
        }
        return true;
    }

    public String convertDate(int date) {
        String tempDate =  addDate(date);
        String newTempDate = null;
        Date newTempDateold = new Date(tempDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        newTempDate = sdf.format(newTempDateold);
        return newTempDate;
    }

    public boolean scheduleDateIsHoliday(Batch batch) {
        String day = findDay(batch.days);
        String convertedNewTempDate = convertDate(batch.days);
        boolean effectiveDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);
        return effectiveDate;
    }


    public String getDate( Batch batch){
        String day = findDay(batch.days);
        String convertedNewTempDate = convertDate(batch.days);
        boolean effectiveDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);

        if(day == "SATURDAY"){
            if(batch.credit){
                convertedNewTempDate = convertDate(batch.days-1);
                boolean effDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);
                if (!effDate){
                   day = addDate(batch.days-1);
                } else {
                    day = addDate(batch.days-2);
                }
            } else if(batch.debit){
                convertedNewTempDate = convertDate(batch.days+2);
                boolean effDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);
                if (!effDate){
                    day = addDate(batch.days+2);
                } else {
                    day = addDate(batch.days+3);
                }

            }
        } else if (day == "SUNDAY"){
            if(batch.credit){
                convertedNewTempDate = convertDate(batch.days-2);
                boolean effDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);
                if (!effDate){
                    day = addDate(batch.days-2);
                } else {
                    day = addDate(batch.days-3);
                }

            } else if(batch.debit){
                convertedNewTempDate = convertDate(batch.days+1);
                boolean effDate = checkIfEffectiveDateIsHoliday(convertedNewTempDate);
                if (!effDate){
                    day = addDate(batch.days+1);
                } else {
                    day = addDate(batch.days+2);
                }

            }
        } else  {
            day = addDate(batch.days);
        }
        return day;
    }

    public String getRandomRoutingNumber() {
        String routingNumbers[] = {"123123123", "053000196", "272477694", "053208037","221971293","021000128","322284928","051050009","101205131","114900685","051000253","051402961"};
        Random rand = new Random();
        int n = rand.nextInt(routingNumbers.length);
        return routingNumbers[n];
    }

    public String getRandomAccountNumber() {
        long timeSeed = System.nanoTime();
        double randSeed = Math.random() * 1000;
        long midSeed = (long) (timeSeed * randSeed);
        String s = midSeed + "";
        String subStr = s.substring(0, 9);
        return subStr;
    }

    public String getCustomerName(){
        Random rand = new Random();
        int n = rand.nextInt(10000000) + 100;
        String customerName = "CustomerName: " + n;
        return customerName;
    }

    public String getCustomerId(){
        long timeSeed = System.nanoTime();
        double randSeed = Math.random() * 1000;
        long midSeed = (long) (timeSeed * randSeed);
        String s = midSeed + "";
        String subStr = s.substring(0, 3);
        return subStr;
    }

    public String getAmountDecimal() {
        Random r = new Random();
        String amount = String.valueOf(r.nextInt(50 + 1) / 10.0);
        double tempAmount = Double.parseDouble(amount);
        while (tempAmount <=1.0) {
            amount = String.valueOf(r.nextInt(50 + 1) / 10.0);
            tempAmount = Double.parseDouble(amount);
        }
        String finalAmount = Double.toString(tempAmount);
        return finalAmount;
    }
    
    public String enterAmountDecimal(String amount) {
        double newAmountT = Double.parseDouble(amount) *10 ;
        String newAmount = String.valueOf(newAmountT);
        System.out.println("printing newAmount :" + newAmount);
        return newAmount;
    }

    public int getRandomAccount(int min, int max) {
        

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) - min;
    }

    public String getAddenda(){
        Random rand = new Random();
        int n = rand.nextInt(10000000) + 100;
        String addenda = "addenda record: " + n;
        return addenda;
    }


}





