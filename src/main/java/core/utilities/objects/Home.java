package core.utilities.objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Home {

    public String sbAccountEntity;
    public String sbAccountNum;
    public String anotherSBAccount;
    public double availableBalance;
    public double holdAmount;
    public int dateRange;
    public double totalPendingTransaction;
    public int pendingTransactionSize;
    public String transactionTotal;
    public int accountIndex;
    public int toAccountIndex;
    public String toAccount;
    public boolean newUser;
    public boolean oneAccount;
    public boolean newUserPopup;
    public boolean simpleSB;
    public boolean message;
    public String frequency;
    public int internalAccountSize = 0;
    public String accountType;
    public String category1;
    public String category2;
    public String category3;
    public String categoryMonth;
    public String categoryPeriodRemaining;
    public int categoryAmountRandom1 = 0;
    public int categoryAmountRandom2 = 0;
    public int categoryAmountRandom3 = 0;
    public boolean multipleCategorySet;
    public String totalSpendingMonth;
    public String accountDesc;
    public boolean commercial;
    public String tsWeeklyStartOn;
    public String tsMyLimit;
    public String tsMonthlyStartOn;
    public String tsTwiceaMonthOn;
    public int tsEvery2WeekOn;
    public boolean tsWeekly;
    public boolean tsMonthly;
    public boolean tsTwiceAMonth;
    public boolean tsEvery2Week;
    public boolean tsPayPeriod;
    public String firstDayOfMonth;
    public String lastDayOfMonth;
    public String firstDayOfPreviousMonth;
    public String lastDayOfPreviousMonth;
    public boolean spendingBreakdownCurrent;
    public boolean spendingBreakdownPrevious;
    public boolean mimoCategory;
    public float finalAmount = 0;
    public float transactionFinalAmount = 0;
    public int noOfCategory = 0;
    public boolean tsAlerts;
    public boolean cwAlerts;
    public boolean euAlerts;
    public int totalSpendingSpent;

    public HashMap<Integer, String> dateTransactionMap = new HashMap<Integer, String>();
    public HashMap<Integer, String> descriptionTransactionMap = new HashMap<Integer, String>();
    public HashMap<Integer, String> amountTransactionMap = new HashMap<Integer, String>();

    public HashMap<Object, Object> accountMap = new HashMap<>();
    public HashMap<Integer, Object> accountMaptest = new HashMap<>();
    public HashMap<Object, Object> accountMapReverse = new HashMap<>();

    public HashMap<Integer, String> accountEntityMap = new HashMap<>();
    public HashMap<Integer, Object> balanceAvailableMap = new HashMap<Integer, Object>();
    public HashMap<Integer, Object> balanceCurrentMap = new HashMap<Integer, Object>();

    public HashMap<Integer, Object> accountDescriptionMap = new HashMap<Integer, Object>();
    public HashMap<Integer, Object> accountDescriptionMapTemp = new HashMap<Integer, Object>();

    public Object mySpendingAccount = new ArrayList<>();
    public List<Object> categoryWatchCategoryName = new ArrayList<>();
    public HashMap<String, Object> categoryWatchTransactionCount = new HashMap<>();
    public HashMap<String, String> categoryWatch_Expense = new HashMap<>();
    public HashMap<String, String> categoryWatch_Income = new HashMap<>();
    public List<String> categoryWatchList = new ArrayList<>();
    public List<String> categoryWatchName = new ArrayList<>();
    public List<String> tempMap = new ArrayList<>();
    public List<Integer> tempMap1 = new ArrayList<>();
    public List<String> shortDescriptionName = new ArrayList<>();
    public List<String> amountList = new ArrayList<>();
    public HashMap<String, String> shortDesc_Amount = new HashMap<>();
    public HashMap<String, String> categoryMap = new HashMap<>();
    public List<String> categoryName = new ArrayList<>();
    public HashMap<String, String> notificationsStatus = new HashMap<>();
    public List<String> notificationType = new ArrayList<>();


    public int getRandomAccount(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) - min;
    }

    public String getAmount() {
        Random r = new Random();
        String amount = String.valueOf(r.nextInt(500) + 1);
        return amount;
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






}
