package pages;

import core.basePages.AllCommunityPages;
import core.basePages.BaseCommunityPage;
import core.utilities.baseUtilities.Browser;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetStartedPage extends BaseCommunityPage {
    
    @FindBy(xpath=".//span[@class='MuiButton-label' and contains(text(),'Close')]")
    public WebElement demoClose;
    
    @FindBy(xpath=".//span[contains(text(),'Get Started')]")
    public WebElement getStarted;
    
    @FindBy(xpath=".//span[contains(text(),'Dash')]")
    public WebElement dash;
    
    @FindBy(xpath=".//span[contains(text(),'Schedule')]")
    public WebElement schedule;
    
    @FindBy(xpath=".//span[contains(text(),'Customers')]")
    public WebElement customers;
    
    @FindBy(xpath=".//span[contains(text(),'My Apps')]")
    public WebElement myApps;
    
    @FindBy(xpath=".//span[contains(text(),'My Money')]")
    public WebElement myMoney;
    
    @FindBy(xpath=".//span[contains(text(),'Reporting')]")
    public WebElement reporting;
    
    @FindBy(xpath=".//span[contains(text(),'Price Book')]")
    public WebElement priceBook;
    
    @FindBy(xpath="//span[contains(text(),'NEW')]")
    public WebElement newClick;
    
    @FindBy(xpath=".//span[contains(text(),'Job')]")
    public WebElement newJob;
    
    
    public GetStartedPage(Browser browser, AllCommunityPages allPages) {
        
        super(browser, allPages);
        
    }

    @Override
    protected ExpectedCondition getPageLoadExpectedConditions() {
        return ExpectedConditions.and(
                ExpectedConditions.elementToBeClickable(getStarted),
                ExpectedConditions.elementToBeClickable(dash)
        );
    }
    
    public void validateAllWebLinks() {
        List<WebElement> links = driver.findElements(By.xpath(".//a"));
        System.out.println("No of links are " + links.size());
        System.out.println(links);
        
        List<String> urllist = new ArrayList<String>();
        String url=null;
        
        for(WebElement e : links) {
            url = e.getAttribute("href");
            urllist.add(url);
        }
        
        urllist.parallelStream().forEach(e -> checkBrokenLink(e));
        
        System.out.println(url);
    }
    
    public void checkBrokenLink (String linkUrl) {
        
        try{
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            
            if(httpURLConnection.getResponseCode() >=400) {
                System.out.println(linkUrl + "---> " + httpURLConnection.getResponseMessage() + " is a broken link");
                browser.logTestAction(linkUrl + "---> " + httpURLConnection.getResponseMessage() + " is a broken link");
            } else {
                System.out.println(linkUrl + "--> " + httpURLConnection.getResponseMessage());
            }
            
        } catch (Exception e) {
        
        }
        
    }
    
    public void clickNewJob() {
        clickElementOnceVisible(newClick);
        clickElementOnceVisible(newJob);

    }
    
   
    
    
}
