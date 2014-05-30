package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by igor on 5/21/14.
 */
public class Executor {

    public static int execUpdate(Connection connection, String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        int updated = stmt.getUpdateCount();
        stmt.close();

        return updated;
    }
    public static UserDataSet execQuery(Connection connection,
                                  String query,
                                  ResultHandler handler) throws SQLException {

        Statement stmt = connection.createStatement();
        stmt.execute(query);
        ResultSet result = stmt.getResultSet();
        UserDataSet user = handler.handle(result);
        result.close();
        stmt.close();

        return user;
    }

}
