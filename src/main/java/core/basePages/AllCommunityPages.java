package core.basePages;

import core.utilities.baseUtilities.Browser;
import pages.Other.*;


public class AllCommunityPages {

    public LoginPage login;
    public AuthPage auth;
    public OverviewPage home;

    //mfa pages
    public MFAPage mfaPage;


    public AllCommunityPages(Browser b){
        login = new LoginPage(b, this);
        auth = new AuthPage(b, this);
        home = new OverviewPage(b, this);
        
     }
}
