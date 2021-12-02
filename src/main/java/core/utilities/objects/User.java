package core.utilities.objects;

import com.github.javafaker.Faker;
import com.google.gson.JsonObject;
import core.utilities.enums.AccountTypes;
import core.utilities.enums.Institutions;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import core.utilities.enums.States;

public class  User extends CoreUser {

    public Institutions institution;
    public String accessId;
    public String agentId;
    public boolean subUser;
    public String subuserAccessId;
    public String randomValue;
    public String accountEntity;
    public String accountType;
    public String institutionID;
    public String environment;
    public String focusEntity;
    public String date;
    public String endDate;
    public String debitCards;

    public Address address;
    public String ssn;
    public String birthday;
    public String primaryPhone;
    public String secondaryPhone;

    public String customerId;

    public String secondaryEmailAddress;

    public String memberNumber;
    public String customerPIN;
    public String referredBy;

    public String firstSecurityAnswer;
    public String secondSecurityAnswer;
    public String thirdSecurityAnswer;

    public Account ownedAccount;
    public String custPropName;
    public String State;

    public boolean receiveElectronicStatements;

    public User(Institutions institution) {
        randomValue = UUID.randomUUID().toString().replaceAll("-","");
        address = new Address();
        ownedAccount = new Account(this, AccountTypes.Checkings);
        this.institution = institution;
    }

    public User() {
        randomValue = UUID.randomUUID().toString().replaceAll("-","");
        address = new Address();
        ownedAccount = new Account(this, AccountTypes.Checkings);
    }

    public User(String accessId, String password, Institutions institution) {
        this(institution);
        this.accessId = accessId;
        this.password = password;
        this.institution = institution;
    }

    public String getFullName() {
        return first + " " + last;
    }

    public String getBusinessName() { return getFullName() + " LLC"; }

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

    @Override
    public User populateRandom() {
        try {
            Faker faker = new Faker();
            first = faker.name().firstName();
            last = faker.name().lastName();
            first = StringUtils.capitalize(first);
            last = StringUtils.capitalize(last);
            middle = first.substring(0,2) + last.substring(0,2);

            address.streetAddress1 = faker.address().streetAddress();
            address.streetAddress2 = faker.address().secondaryAddress();
            address.setCity(faker.address().city());
            State = faker.address().state().intern();
            address.zip = faker.address().zipCode();

            //making sure initials or state has no variations of the password
            String initials = (first.subSequence(0,1)+last.substring(0,1));
            while(initials.contains("LE") || State.equals("Maine") || State.equals("Indiana")){
                first = faker.name().firstName();
                last = faker.name().lastName();
                first = StringUtils.capitalize(first);
                last = StringUtils.capitalize(last);
                middle = first.substring(0,2) + last.substring(0,2);
                State = faker.address().state();
                address.zip = faker.address().zipCode();
                if(initials.contains("LE") || State.equals("Maine") || State.equals("Indiana")){
                    break;
                }
            }

            email = getEmail();
            secondaryEmailAddress = getSecondaryEmail();
            password = "letmein123";
            firstSecurityAnswer = "test";
            secondSecurityAnswer = "test";
            thirdSecurityAnswer = "test";
            customerId = "";
            //accessId can only be up to 20 characters
            accessId = email.substring(0, 20);
            birthday = faker.date().birthday().toInstant().toString();
            birthday = birthday.substring(0, birthday.indexOf("T"));
            String[] temporary =birthday.split("-");
            birthday = temporary[1]+"/"+temporary[2]+"/"+temporary[0];
            System.out.println("BIRTHDAY:"+birthday);
            primaryPhone = faker.phoneNumber().phoneNumber().replaceAll("\\D+","");
            while(String.valueOf(primaryPhone.charAt(0)).equals("0") | primaryPhone.length()>12){
                primaryPhone = faker.phoneNumber().phoneNumber().replaceAll("\\D+","");
            }
            secondaryPhone = faker.phoneNumber().cellPhone().replaceAll("\\D+","");
            while(String.valueOf(secondaryPhone.charAt(0)).equals("0") | String.valueOf(secondaryPhone.charAt(0)).equals("1")  | secondaryPhone.length()>12){
                secondaryPhone = faker.phoneNumber().phoneNumber().replaceAll("\\D+","");
            }
            ssn = (String.valueOf(faker.number().randomNumber(9,true)));
        } catch (Exception e) {
            e.printStackTrace();
            first = "Test";//UUID.randomUUID().toString().replaceAll("\\d","").replaceAll("-","");
            last = "User";//UUID.randomUUID().toString().replaceAll("\\d","").replaceAll("-","");
            middle = first.substring(0,2) + last.substring(0,2);
            email = getEmail();
            password = "letmein123";
            accessId = email.substring(0, 19);

            address.streetAddress1 = "123 Main St";
            address.streetAddress2 = "Suite 1a";
            address.setCity("Pittsburgh");
            address.state = States.PENNSYLVANIA;
            address.zip = "12345";

            birthday = "08/29/1984";

            primaryPhone = "5402181212";
            secondaryPhone = "5402181313";
            ssn = "2" + Integer.toString(10000000 + new Random().nextInt(90000000));
            firstSecurityAnswer = "test";
            secondSecurityAnswer = "test";
            thirdSecurityAnswer = "test";
            customerId = "";
            accessId = email.substring(0, email.indexOf("@"));
        }
        return this;
    }

    private String getEmail() {
        String email = String.format("%s.%s.%s@apitureqa.com",  StringUtils.capitalize(first), StringUtils.capitalize(last), randomValue);

        if (email.length() > 60){
            randomValue = randomValue.substring(0, randomValue.length() - (email.length() - 60));
            return getEmail();
        }

        return email;
    }

    private String getSecondaryEmail() {
        String email = String.format("%s.%s.%s@apitureqa.com",  StringUtils.capitalize(first), StringUtils.capitalize(last), randomValue);

        if (email.length() > 40){
            randomValue = randomValue.substring(0, randomValue.length() - (email.length() - 40));
            return getEmail();
        }

        return email;
    }

    private String cleanPhoneNumbers(String original) {
        char[] num = original.
                replace("(", "")
                .replace(")", "")
                .replaceAll("-", "")
                .toCharArray();

        for (int pos : new int[]{0, 3}) {
            if (num[pos] == '0' || num[pos] == '1') {
                num[pos] = Integer.toString(ThreadLocalRandom.current().nextInt(2, 9)).toCharArray()[0];
            }
        }

        return new String(num);
    }

    public String getRandomAccount() {
        String checkingAccNumbers[] = {"2018062201","2018062202","2018062203","2018062204","2018062205","2018062206","2018062207","2018062208","2018062209"};
        Random rand = new Random();
        int n = rand.nextInt(checkingAccNumbers.length);
        return checkingAccNumbers[n];
    }

    public String getTacticalAccessId() {
        Random rand = new Random();
        int n = rand.nextInt(100000) + 100;
        String tacticalAccessId = "TactStrike:"+n;
        return tacticalAccessId;
    }

    public String getSubUserAccessId() {
        Random rand = new Random();
        int n = rand.nextInt(100000) + 100;
        String tacticalAccessId = "SubUser:"+n;
        return tacticalAccessId;
    }

    private class RandomUserApiUser {
        public class Name {
            public String title;
            public String first;
            public String last;
        }

        public class Location {
            public Street street;
            public String city;
            public String state;
            public int postcode;
        }

        public class Street{
            public int number;
            public String name;
        }

        public class Login {
            public String username;
            public String password;
            public String salt;
            public String md5;
            public String sha1;
            public String sha256;
        }

        public class Id {
            public String name;
            public String value;
        }

        public class Picture {
            public String large;
            public String medium;
            public String thumbnail;
        }

        public class Result {
            public String gender;
            public Name name;
            public Location location;
            public String email;
            public Login login;
            public JsonObject dob;
            public JsonObject registered;
            public String phone;
            public String cell;
            public Id id;
            public Picture picture;
            public String nat;
        }

        public class Info {
            public String seed;
            public int results;
            public int page;
            public String version;
        }

        public List<Result> results;
        public Info info;

    }


}