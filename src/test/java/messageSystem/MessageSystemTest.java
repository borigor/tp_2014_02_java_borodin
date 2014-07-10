package messageSystem;

import db.AccountService;
import frontend.Frontend;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * Created by igor on 5/31/14.
 */
public class MessageSystemTest {

    private Address address1 = new Address();
    private Address address2 = new Address();
    private MessageSystem ms = new MessageSystem();
    private Frontend frontend = mock(Frontend.class);
    private AccountService service = mock(AccountService.class);
    private String login = getRandomString(10);
    private String pass = getRandomString(10);
    private String sessionID = getRandomString(15);

    @Before
    public void setUp() {
        (new Thread(frontend)).start();
        (new Thread(service)).start();
        when(frontend.getAddress()).thenReturn(address1);
        when(service.getAddress()).thenReturn(address2);
        when(service.getMessageSystem()).thenReturn(ms);
        ms.addService(frontend);
        ms.addService(service);
        ms.getAddressService().setAccountService(service.getAddress());
        ms.getAddressService().setAccountService(service.getAddress());
    }

    @Test
    public void testRegMessage() throws SQLException {

        ms.sendMessage(new MsgRegistration(address1, address2, login, pass, sessionID));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ms.execForAbonent(service);
        verify(service, atLeastOnce()).registration(anyString(), anyString());
    }

    @Test
    public void testLoginMessage() throws SQLException {
        ms.sendMessage(new MsgLogin(address1, address2, login, pass, sessionID));

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ms.execForAbonent(service);
        verify(service, atLeastOnce()).login(anyString(), anyString());
      }

    public static String getRandomString(int length) {
        Random random = new Random();
        char from[] = "abcdefghijklmnopqrstuvwxyzABCEFGHIGKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(from[random.nextInt((from.length))]);
        }
        return result.toString();
    }
}

