package templater;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by igor on 5/30/14.
 */
public class PageGeneratorTest {

    @Test
    public void testUserIDPage() {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("refreshPeriod", "1000");
        pageVariables.put("serverTime", "12.12.12");
        pageVariables.put("userId", "1234");
        Assert.assertTrue(PageGenerator.getPage("userID.tml", pageVariables).contains("Timer"));
    }

    @Test
    public void testRegistrationPage() {
        Map<String, Object> pageVariables = null;
        Assert.assertTrue(PageGenerator.getPage("registration.tml", pageVariables).contains("registration"));
    }
}
