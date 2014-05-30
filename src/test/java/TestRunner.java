import db.DataBaseTests;
import frontend.RoutingTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import templater.PageGeneratorTest;

/**
 * Created by igor on 5/30/14.
 */
public class TestRunner {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(DataBaseTests.class, PageGeneratorTest.class, RoutingTest.class);

        if (result.wasSuccessful()) {
            System.out.println("\n All tests are success");
        }

        else {
            for (Failure failure: result.getFailures()) {
                System.out.println("\n Failed: " + failure.toString() + " \n \n ===== \n");
            }
        }
    }
}
