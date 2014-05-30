package db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by igor on 5/21/14.
 */
public class UserDAO {

    Connection connection;
    ResultHandler handler;

    UserDAO(Connection connection) {
        this.connection = connection;
        this.handler = new ResultHandler();
    }

    int addUser(UserDataSet user) throws SQLException {
        return Executor.execUpdate(connection, SqlQueryConstructor.constructUpdate(user));
    }

    UserDataSet findUser(String login) throws SQLException {
        return Executor.execQuery(connection, SqlQueryConstructor.constructSelect(login), handler);
    }


}
