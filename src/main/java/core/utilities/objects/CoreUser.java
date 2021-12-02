package core.utilities.objects;

import com.github.javafaker.Faker;
import core.utilities.enums.States;

import java.util.UUID;

public abstract class CoreUser {

    public String testDomain;
    public String uuid;
    public String username;
    public String email;
    public String password;
    public String first;
    public String last;
    public String middle;
    public Address primaryAddress;
    public Address secondaryAddress;
    public String cellPhone;
    public String homePhone;
    public UserType userType;
    private Integer maxEmailLength;

    public CoreUser() {
        testDomain = "apitureqa.com";
        setMaxEmailLength(50);
    }

    public CoreUser populateRandom() {
        this.uuid = UUID.randomUUID().toString();
        this.first = Faker.instance().name().firstName();
        this.middle = Faker.instance().name().name();
        this.last = Faker.instance().name().lastName();
        this.cellPhone = Faker.instance().phoneNumber().cellPhone();
        this.homePhone = Faker.instance().phoneNumber().phoneNumber();

        this.primaryAddress = new Address();
        this.primaryAddress.streetAddress1 = Faker.instance().address().streetAddress();
        this.primaryAddress.streetAddress2 = Faker.instance().address().secondaryAddress();
        this.primaryAddress.city = Faker.instance().address().city();
        this.primaryAddress.state = States.parse(Faker.instance().address().state());
        this.primaryAddress.zip = Faker.instance().address().zipCode();

        this.secondaryAddress = new Address();
        this.secondaryAddress.streetAddress1 = Faker.instance().address().streetAddress();
        this.secondaryAddress.streetAddress2 = Faker.instance().address().secondaryAddress();
        this.secondaryAddress.city = Faker.instance().address().city();
        this.secondaryAddress.state = States.parse(Faker.instance().address().state());
        this.secondaryAddress.zip = Faker.instance().address().zipCode();

        this.email = buildEmail();
        this.username = this.email;
        this.password = "LetMeIn!23";
        return this;
    }

    public String buildEmail() {
        String format = "%s.%s.%s@%s";
        String uuid = this.uuid.replaceAll("-", "");
        String email = String.format(format, first, last, uuid, testDomain);

        if (email.length() <= getMaxEmailLength()) {
            return email;
        } else {
            Integer dif = email.length() - getMaxEmailLength();
            return String.format(format, first, last, uuid.substring(0, uuid.length() - dif), testDomain);


        }
    }

    public Integer getMaxEmailLength() {
        return maxEmailLength;
    }

    public void setMaxEmailLength(Integer maxEmailLength) {
        this.maxEmailLength = maxEmailLength;
    }

    public UserType getUserType() {
        return userType;
    }
}
