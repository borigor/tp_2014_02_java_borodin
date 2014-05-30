package frontend;

import db.AccountService;
import db.DBStatus;
import db.SqlQueryConstructor;
import db.UserDataSet;
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by igor on 3/6/14.
 */
public class Frontend extends HttpServlet {

    private AtomicLong userIdGenerator = new AtomicLong();
    private AccountService accountService = new AccountService();


    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> pageVariables = new HashMap<String, Object>();

        switch (request.getPathInfo()) {

            case ("/userID"):
                getTimerPage(response, request, pageVariables);
                break;

            case ("/registration"):
                getRegistrationPage(response, pageVariables);
                break;

            case ("/"):
                response.sendRedirect("/");
                break;
        }
    }

    public void getTimerPage(HttpServletResponse response, HttpServletRequest request,
                             Map<String, Object> pageVariables) throws IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/");
            return;
        }
        pageVariables.put("refreshPeriod", "1000");
        pageVariables.put("serverTime", getTime());
        pageVariables.put("userId", userId);
        response.getWriter().println(PageGenerator.getPage("userID.tml", pageVariables));
    }

    public void getRegistrationPage(HttpServletResponse response,
                                    Map<String, Object> pageVariables) throws IOException {
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
                    postRegistrationPage(request, response, accountService);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case ("/"):
                try {
                    postMainPage(response, request, accountService, userIdGenerator);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void postRegistrationPage(HttpServletRequest request, HttpServletResponse response,
                                     AccountService accountService) throws SQLException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        DBStatus regUser = accountService.registration(login, pass);
        if ((regUser == DBStatus.LoginError) || (regUser == DBStatus.UserExist)) {
            response.sendRedirect("/");
        }

        HttpSession session = request.getSession();
        if( !session.isNew() ) {
            session.invalidate();
            session = request.getSession();
        }
        Long userId = (Long) userIdGenerator.getAndIncrement();
        session.setAttribute("userId", userId);
        response.sendRedirect("/userID");

    }

    public void postMainPage(HttpServletResponse response, HttpServletRequest request,
                             AccountService accountService, AtomicLong userIdGenerator) throws SQLException, IOException {

        final String login = request.getParameter("login");
        final String pass = request.getParameter("pass");

        DBStatus loginUser = accountService.login(login, pass);
        if ((loginUser == DBStatus.LoginError) || (loginUser == DBStatus.UserExist)) {
            response.sendRedirect("/");
        }

        HttpSession session = request.getSession();
        if( !session.isNew() ) {
            session.invalidate();
            session = request.getSession();
        }
        Long userId = (Long) userIdGenerator.getAndIncrement();
        session.setAttribute("userId", userId);
        response.sendRedirect("/userID");
    }

}

