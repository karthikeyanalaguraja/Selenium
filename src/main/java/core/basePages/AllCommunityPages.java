package core.basePages;

import core.utilities.baseUtilities.Browser;
import pages.LoginPage;
import pages.*;


public class AllCommunityPages {

    public LoginPage login;
    public GetStartedPage getStarted;
    public SignUpPage signUpPage;
    public NewJobPage newJobPage;
   
    public AllCommunityPages(Browser b){
        login = new LoginPage(b, this);
        getStarted = new GetStartedPage(b, this);
        signUpPage = new SignUpPage(b, this);
        newJobPage = new NewJobPage(b, this);
    
    
    }
}
