package db;

import messageSystem.Abonent;
import messageSystem.Address;
import messageSystem.MessageSystem;

import java.sql.SQLException;

/**
 * Created by igor on 5/23/14.
 */
public class AccountService implements AccountServiceInterface, Runnable, Abonent {

    private UserDAO dao = null;
    private Address address;
    private MessageSystem messageSystem;

    public AccountService(MessageSystem messageSystem) {
        dao = new UserDAO(Connector.getConnection());
        this.messageSystem = messageSystem;
        this.address = new Address();
        messageSystem.addService(this);
        messageSystem.getAddressService().setAccountService(address);
    }

    @Override
    public DBStatus registration(String login, String passd) throws SQLException {
        dao = new UserDAO(Connector.getConnection());

        if (dao.findUser(login) != null)
            return DBStatus.UserExist;
        if (!isCorrectLogin(login, passd))
            return DBStatus.LoginError;

        dao.addUser(new UserDataSet(login, passd));
        return DBStatus.Ok;
    }

    @Override
    public DBStatus login(String login, String passd) throws SQLException {
        dao = new UserDAO((Connector.getConnection()));

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

    public Long getUserId(String login) throws SQLException {
        dao = new UserDAO(Connector.getConnection());
        UserDataSet user = dao.findUser(login);
        if (user != null)
            return dao.findUser(login).getId();
        else
            return null;

    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
