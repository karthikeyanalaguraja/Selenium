package core.utilities.objects;

import core.utilities.enums.States;

public class Address {
    public String streetAddress1;
    public String streetAddress2;
    public String city;
    public States state;
    public String zip;

    public String getCity() {return city;}

    public void setCity(String city) { this.city = city; }
}
