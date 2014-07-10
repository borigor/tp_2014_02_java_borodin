package frontend;

import messageSystem.MessageSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by igor on 5/30/14.
 */
public class RoutingTest {

    final private static HttpServletRequest request = mock(HttpServletRequest.class);
    final private static HttpServletResponse response = mock(HttpServletResponse.class);
    final private static HttpSession session = mock(HttpSession.class);

    private Frontend frontend = null;
    private StringWriter stringWriter = null;
    private PrintWriter printWriter= null;
    private String url = null;

    @Before
    public void setUp() throws IOException {
        frontend = new Frontend(new MessageSystem());
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
    }

    @Test
    public void testRegistrationRoute() throws ServletException, IOException {
        url = "/registration";
        when(request.getPathInfo()).thenReturn(url);
        when(response.getWriter()).thenReturn(printWriter);

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("registration"));
    }

    @Test
    public void testTimerRoute() throws IOException, ServletException {
        url = "/userID";
        when(request.getPathInfo()).thenReturn(url);
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(printWriter);
        when(session.getAttribute(anyString())).thenReturn(anyLong());

        frontend.doGet(request, response);
        Assert.assertTrue(stringWriter.toString().contains("Timer"));
    }
}
