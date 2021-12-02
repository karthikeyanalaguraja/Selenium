package core.utilities.objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class Bill {
    public Account toAccount;
    public Account fromAccount;
    public String amount;
    public String deliverDate;


    public Bill createRandomBillAmountAndDateOnly(){
        Bill bill = new Bill();
        bill.amount = createRandomAmount();
        bill.deliverDate = createRandomFutureDate(5);
        return bill;
    }

    private String createRandomAmount(){
        int randomNum = ThreadLocalRandom.current().nextInt(15, 501);
        String amount = String.valueOf(randomNum);
        System.out.println(amount);
        return amount;
    }

    private String createRandomFutureDate(int daysInFuture){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if(daysInFuture == 0) {
            LocalDate localDate = LocalDate.now();
            return dtf.format(localDate);
        } else {
            LocalDate localDate = LocalDate.now().plusDays(daysInFuture);
            return dtf.format(localDate);
        }
    }
}
