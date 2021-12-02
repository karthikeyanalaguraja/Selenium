package core.utilities.enums;

public enum Owner {
    Kevin("kevin.gay@apiture.com"), // Still being used
    Tim("timothy.tripp@apiture.com"), // Still being used
    Karthik("Karthikeyan.Alaguraja@Apiture.com"),
    Anthony("Anthony.Kent@apiture.com"),
    Chase("Chase.Clayton@Apiture.com"),
    Maxwell("Maxwell.Falcon@apiture.com"),
    Zyrus("zyrus.johnson@apiture.com"),
    Aza("aza.kolbaev@apiture.com"),
    Prabhu("prabhu.rk@aptirue.com"),
    Rachana("rachana.chavda@apiture.com");


    String email;

    Owner(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
