package core.utilities.annotations;

import core.utilities.enums.Owner;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCase {

    /**
     * The creator/owner of this test
     */
    Owner owner();

    /**
     * The Test Case ID corresponding to this test method.
     *
     * @return
     */
    int testId();

    /**
     * A brief description of the Test Case being executed.
     *
     * @return
     */

    boolean smokeTest() default false;

    /**
     * To define a test as Smoke test
     *
     * @return
     */

    String testDescription() default "";
}
