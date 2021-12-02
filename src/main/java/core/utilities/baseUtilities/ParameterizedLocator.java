package core.utilities.baseUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.How;


/**
 * This class should be used to locate elements on a page that have parameterized css or xpath locators
 * Parameters are set using the setParameters() method an user the format syntax of the String.format() method
 */
public class ParameterizedLocator {

    How locatorType = How.UNSET;
    String parameterizedLocator;
    String locator;

    /**
     *
     * @param locatorType Type of Locator used EX: XPATH, CSS
     * @param parameterizedLocator Locator string with parameters. Parameters user the String.format() syntax.
     *   Parameters are resolved when you call the setParameters() method.
     */
    public ParameterizedLocator(How locatorType, String parameterizedLocator){
        this.locatorType = locatorType;
        this.parameterizedLocator = parameterizedLocator;
    }

    /**
     *
     * @param locatorType Type of Locator used EX: XPATH, CSS
     * @param parameterizedLocator Locator string with parameters. Parameters user the String.format() syntax.
     * @param parameters An array of parameters for the ParameterizedLocator()
     */
    public ParameterizedLocator(How locatorType, String parameterizedLocator, Object ... parameters){
        this.locatorType = locatorType;
        this.parameterizedLocator = parameterizedLocator;
        setParameters(parameters);
    }

    /**
     * @param parameters An array of parameters for the ParameterizedLocator()
     */
    public void setParameters(Object ... parameters){
        locator = String.format(parameterizedLocator,parameters);
    }

    /**
     * Returns a By object from parameterized locator. Don't call unless setParameters() has been called first
     * @return
     */
    public By getLocator(){
        return locatorType.buildBy(locator);
    }

    /**
     *
     * @param parameters An array of parameters that is set before returning the locator
     * @return
     */
    public By getLocator(Object ... parameters){
        setParameters(parameters);
        return locatorType.buildBy(locator);
    }

}
