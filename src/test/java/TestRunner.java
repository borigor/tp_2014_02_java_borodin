import db.DataBaseTest;
import frontend.RoutingTest;
import messageSystem.MessageSystemTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import templater.PageGenerator;
import templater.PageGeneratorTest;

/**
 * Created by igor on 5/31/14.
 */
public class TestRunner {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(DataBaseTest.class, PageGeneratorTest.class, RoutingTest.class,
                MessageSystemTest.class);

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
