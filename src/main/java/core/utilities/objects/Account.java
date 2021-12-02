package core.utilities.objects;

import core.utilities.enums.AccountTypes;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Account {
    public User accountOwner;

    public String accountNumber;
    public String accountDescription;
    public String accountNickname;
    public AccountTypes accountType;
    public boolean billPay;
    public String rountingAndTransit;
    public String transferLimit;
    public String enterTransferLimit;
    public List<Account> accountList;

    public Account() {

    }

    public Account( User owner, AccountTypes accountType ) {
        this.accountOwner = owner;
        this.accountType = accountType;
        accountNickname = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 30);
        Random rand = new Random();
        accountNumber = Integer.toString(100000000 + rand.nextInt(900000000));
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
        String routingNumbers[] = {"123123123", "053000196", "272477694", "053208037", "221971293","021000128","322284928","051050009","101205131","114900685","051000253","051402961"};
        System.out.println(routingNumbers.length);
        Random rand = new Random();
        int n = rand.nextInt(routingNumbers.length);
        return routingNumbers[n];

    }

    public String getRandomAccountType() {
        String accountType[] = {"External Checking","External Savings"};
        Random rand = new Random();
        int n = rand.nextInt((accountType.length));
        return  accountType[n];
    }

    public String getRandomBankName() {
        String bankName[] = {"Foo Bank", "Bank of America", "Zeal Credit Union", "Capital Bank, Chase Bank, Wells Fargo, HDFC Bank"};
        System.out.println(bankName.length);
        Random rand = new Random();
        int n = rand.nextInt(bankName.length);
        return bankName[n];
    }

}

