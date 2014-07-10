package frontend;

import db.AccountService;
import db.DBStatus;
import db.SqlQueryConstructor;
import db.UserDataSet;
import messageSystem.*;
import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by igor on 3/6/14.
 */
public class Frontend extends HttpServlet implements Runnable, Abonent {

    private Map<String, UserSession> sessionIdToUserSession = new HashMap<>();
    private Address address;
    private MessageSystem messageSystem;

    public Frontend(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
    }

    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void setUserId(String sessionId, Long userId) {
        UserSession userSession = sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId);
            return;
        }
        userSession.setUserId(userId);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        switch (request.getPathInfo()) {

            case ("/userID"):
                getTimerPage(response, request);
                break;

            case ("/registration"):
                getRegistrationPage(response);
                break;

            case ("/"):
                response.sendRedirect("/");
                break;
        }
    }

    public void getTimerPage(HttpServletResponse response, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        UserSession userSession = sessionIdToUserSession.get(session.getId());
        Long userId = (Long) session.getAttribute("userId");
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("refreshPeriod", "1000");
        pageVariables.put("serverTime", getTime());

        if (userSession == null) {
            pageVariables.put("User", "Auth error");
        } else if (userSession.getUserId() == null) {
            pageVariables.put("User", "Wait for auth");
        } else {
        pageVariables.put("User", "Hello, " + userSession.getLogin() + "! Your UserId = " +
                userSession.getUserId() + "! Your SessionId = " + userSession.getSessionId());
        }

        response.getWriter().println(PageGenerator.getPage("userID.tml", pageVariables));
    }

    public void getRegistrationPage(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<String, Object>();
        pageVariables.put("Status", "");
        response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getSession().invalidate();

        switch (request.getPathInfo()) {

            case ("/registration"):
                try {
                    postRegistrationPage(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case ("/"):
                try {
                    postMainPage(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void postRegistrationPage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        String sessionId = request.getSession().getId();
        UserSession userSession = new UserSession(sessionId, login, messageSystem.getAddressService());
        sessionIdToUserSession.put(sessionId, userSession);

        Address frontendAddress = getAddress();
        Address accountServiceAddress = userSession.getAccountService();

        messageSystem.sendMessage(
                new MsgRegistration(frontendAddress, accountServiceAddress, login, pass, sessionId));

        response.sendRedirect("/userID");
    }

    public void postMainPage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {

        final String login = request.getParameter("login");
        final String pass = request.getParameter("pass");

        String sessionId = request.getSession().getId();
        UserSession userSession = new UserSession(sessionId, login, messageSystem.getAddressService());
        sessionIdToUserSession.put(sessionId, userSession);

        Address frontendAddress = getAddress();
        Address accountServiceAddress = userSession.getAccountService();

        messageSystem.sendMessage(
                new MsgLogin(frontendAddress, accountServiceAddress, login, pass, sessionId));

        response.sendRedirect("/userID");
    }

    @Override
    public void run() {

        while (true) {
            messageSystem.execForAbonent(this);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }
}

