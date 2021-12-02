## Initial Setup
***1. Install Intellij Community/free version***
   https://www.jetbrains.com/idea/download/#section=windows
   
***2. Install Sourcetree***
    https://www.sourcetreeapp.com If prompted, choose the full git install option (if git hasn't been installed prior)

***3. Install Java JDK 9***
   https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase9-3934878.html
   ```x64, Be sure to google "how to install java osx" and follow the steps suggested such as setting environment variables and path properly```
    
***4. Install Maven***
    https://maven.apache.org/download.cgi, Download zip and extract to a folder in your root directory
    ```Be sure to google "how to install maven osx" and follow the steps suggested such as setting environment variables and path properly```
    
***5. Create a folder named ‘browsers’ folder in your root directory (for macOS, put it under /Library in your root directory) and download the latest of each of the following drivers to this folder***
```
    a. chromedriver: https://chromedriver.chromium.org/downloads
    b. geckodriver: https://github.com/mozilla/geckodriver/releases
    *Note: make sure your browser version and lib/browser chromedriver version is same all the time.
```
    
***6. Add the Maven bin folder and the browsers folder path to your System Path environment variable***
```
    a. Mac
        i. Open terminal
        ii. sudo vi /etc/paths
        iii. Press 'i' key to activate editor
        iv. On a new line, add the following:
            1. /Library/browsers
            2. /apache-maven-3.5.2/bin  (NOTE: yours may be a different version, just be sure it's the path the maven bin)
        v. Press 'esc' key then ':wq' to end edit session, write the changes to disc, then quit vi
    b. Windows:
        i. System->Advanced System Settings->Advanced tab->Environment Variables button->Edit the “Path” system variable->add the following paths (modifying to fit your install if different)
            i.1. C:\apache-maven-<YOUR VERSION HERE>\bin
            i.2. C:\browsers
```
            
***7. Navigate to source control***
    https://bitbucket.org/APITURE/communityxpressui/src/master/
```
    a. Click the Clone in SourceTree button
    b. Choose a location to clone the repo (I choose something close to the root directory, such as c:/source/ui)
    c. Create a branch from master (latest)
        i. Click the Branch button
        ii. Enter a name (e.g., 'Nick')
        iii. Click Create Branch
```

***8. Launch IntelliJ***
```
    a. Click the 'Open' option
    b. Navigate to the directory where you cloned your repo (e.g., c:\Source\UI)
    c. Click OK
```

***9. Set initial IntelliJ options***
```
    a. Navigate to File→ Other Settings→ Preferences for New Projects→ Build,Execution,Deployment→Build Tools→ Maven→ Importing
        i. Check 'Import Maven projects automatically
    b. Navigate to Run→ Edit Configurations
        i.Click on Defaults
            i.1. Change Temporary configurations limit to '0'
        ii. Click on Defaults>TestNG
            1. Set Test kind = Class
            2. Leave Class empty
            3. Set VM options = -Dbrowser=Chrome -Drun_locally=true -Denvironment=https://cat2-fxweb.apiture-comm-preprod.com -DuseDocker=true
```
***10. Running tests***
```
    a. Tests can be run individually or as a suite (xml folder):
        i. To run the smoke test, rigth click and run "SmokeTest.xml" file
        ii. To run the regression test, rigth click and run "All.xml" file
        iii. To run tests individually, right click and run each test from test classes under test/java/communityUiTests
```
***11. Allure Report***
```
    Afer xml test suite run, "allure-results" folder gets updated with json files.
    In jenkins, latest Allure report is located on main page of the test pipeline and each test build will have allure icon for report.
```
***12. How to choose the test in Community-tests-ui***
```
    Go to URL,https://build-dev.community.apiture.com/view/Tests/job/community-tests-ui/ click on "Build with Parameters"
    Branch   : could be Master or develop or develop-offshore
    Stage    : choose any branch between (QA, Feature and Cat), Prod and Prodwest are intended for deployment time
    Category : Usages are given below,
        SmokeTest   : This is for CICD, this should be always less than 3 mins
        All         : This run everyday at night {Cat all 7 days, Cat2 M-F}
        Sanity      : This is the combination of Smoke, MFA, Angular and OtherMoneymovents exclude ACH
        ACHTest     : This is all ach test include commercial and Retail
        FXIMTest    : All the test related to FXIM
        Production  : This runs when there is a deployment, while choosing this you need to choose either prod or prodwest from stage
        SSOTest     : This is the place all the Active SSO connection, this test is only runnable in CAT ot CAT2    
```