package core.utilities.enums;

public enum Institutions {

    FOO("https://cat.fundsxpress.com/start/FOO", "1"),
    FOO2("https://cat2-fxweb.apiture-comm-preprod.com/start/FOO", "9"),
    APITURE("https://cat.fundsxpress.com/start/APITURE", "2"),
    APITURE2("https://cat2-fxweb.apiture-comm-preprod.com/start/APITURE", "2"),
    PFPNTN("https://cat.fundsxpress.com/start/PFPNTN", "3"),
    QA1GRIFFIN("https://qa-fxweb.apiture-comm-nonprod.com/start/FOO", "3"),
    FOOPRODUCTION("https://secure.fundsxpress.com/start/FOO", "3"),
    ZTBOI("https://cat2-fxweb.apiture-comm-preprod.com/start/ZTBOI", "1");
    /*PCUWMN("https://uat.fundsxpress.com/start/PCUWMN", "4"),
    PBNNC("https://uat.fundsxpress.com/start/PBNNC", "4"),
    OCBAL("https://uat.fundsxpress.com/start/OCBAL", "4"),
    CSBTT("https://uat.fundsxpress.com/start/CSBTT", "4"),
    ZBODTG("https://uat.fundsxpress.com/start/ZBODTG", "4"),
    NDBTC("https://uat.fundsxpress.com/start/NDBTC", "4"),
    MCUAHIL("https://uat.fundsxpress.com/start/MACUAHIL", "4"),
    NBMPA("https://uat.fundsxpress.com/start/NBMPA", "4"),
    U4ZACUTA("https://uat.fundsxpress.com/start/U4ZACUTA", "5"),
    PBSLTX("https://uat.fundsxpress.com/start/PBSLTX", "6"),
    SCCFCUCA("https://uat.fundsxpress.com/start/SCCFCUCA", "8");*/

    String url;
    String institutionId;

    Institutions(String url, String institutionId) {

        this.url = url;
        this.institutionId = institutionId;
    }

    public String getUrl() {
        return this.url;
    }

    public String getInstitutionId() { return this.institutionId; }

    public String getName() {return this.getUrl().substring(this.getUrl().lastIndexOf("/")+1); }

    public static Institutions findById(String id) {
        Institutions[] institutions = Institutions.values();
        for (Institutions institution : institutions) {
            if (institution.institutionId.equals(id)) {
                return institution;
            }
        }
        return null;
    }
}
