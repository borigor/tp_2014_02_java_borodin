package db;

import java.sql.SQLException;

/**
 * Created by igor on 5/23/14.
 */
public class AccountService implements AccountServiceInterface {

    @Override
    public DBStatus registration(String login, String passd) throws SQLException {
        UserDAO dao = new UserDAO(Connector.getConnection());

        if (dao.findUser(login) != null)
            return DBStatus.UserExist;
        if (!isCorrectLogin(login, passd))
            return DBStatus.LoginError;

        dao.addUser(new UserDataSet(login, passd));
        return DBStatus.Ok;
    }

    @Override
    public DBStatus login(String login, String passd) throws SQLException {
        UserDAO dao = new UserDAO((Connector.getConnection()));

        if (!isCorrectLogin(login, passd))
            return DBStatus.LoginError;

        UserDataSet user = dao.findUser(login);
        if ((user == null) || (!user.getPassword().equals(passd)))
            return DBStatus.LoginError;

        return DBStatus.Ok;
    }

    private boolean isCorrectLogin(String login, String passd) {
        if ((login == null) || (passd == null) || (login.equals("")) || (passd.equals("")))
            return false;
        return true;
    }
}
