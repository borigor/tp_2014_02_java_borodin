package db;

import messageSystem.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

/**
 * Created by igor on 5/31/14.
 */
public class DataBaseTest {

    private String login;
    private String pass;
    private AccountService acService;
    private MessageSystem messageSystem;

    public static String generateRandomString(int lenght) {

        Random random = new Random();

        char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        char[] randomString = new char[lenght];

        for (int i = 0; i < lenght; i++) {
            randomString[i] = characters[random.nextInt(characters.length)];
        }
        return new String(randomString);
    }

    @Before
    public void setUp() {
        messageSystem = new MessageSystem();
        acService = new AccountService(messageSystem);
        login = generateRandomString(10);
        pass = generateRandomString(15);
    }

    @Test
    public void testRegistrationOK() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.registration(login, pass);
        result = resultDB == DBStatus.Ok;

        Assert.assertTrue(result);
    }

    @Test
    public void testRegistrationFailInLogin() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.registration("", pass);
        result = resultDB != DBStatus.Ok;

        Assert.assertTrue(result);
    }

    @Test
    public void testRegistrationFailInPassword() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.registration(login, "");
        result = resultDB != DBStatus.Ok;

        Assert.assertTrue(result);
    }

    @Test
    public void testRegistrationFailExistUser() throws SQLException {
        boolean result;
        DBStatus resultDB;

        acService.registration(login, pass);
        resultDB = acService.registration(login, pass);
        result = resultDB != DBStatus.Ok;

        Assert.assertTrue(result);
    }



    @Test
    public void testLoginOK() throws SQLException {
        boolean result;
        DBStatus resultDB;

        acService.registration(login, pass);
        resultDB = acService.login(login, pass);
        if (resultDB == DBStatus.Ok)
            result = true;
        else
            result = false;

        Assert.assertTrue(result);
    }

    @Test
    public void testLoginFailInLogin() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.login("", pass);
        if (resultDB == DBStatus.Ok)
            result = false;
        else
            result = true;

        Assert.assertTrue(result);
    }

    @Test
    public void testLoginFailInPass() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.login(login, "");
        if (resultDB == DBStatus.Ok)
            result = false;
        else
            result = true;

        Assert.assertTrue(result);
    }

    @Test
    public void testLoginFailAuthPass() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.login(login, pass + "1");
        if (resultDB == DBStatus.Ok)
            result = false;
        else
            result = true;

        Assert.assertTrue(result);
    }

    @Test
    public void testLoginFailAuthLogin() throws SQLException {
        boolean result;
        DBStatus resultDB;

        resultDB = acService.login(login + "1", pass);
        if (resultDB == DBStatus.Ok)
            result = false;
        else
            result = true;

        Assert.assertTrue(result);
    }
}
