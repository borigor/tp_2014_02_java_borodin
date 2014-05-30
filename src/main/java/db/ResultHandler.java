package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by igor on 5/22/14.
 */
public class ResultHandler implements ResultHandlerInterface {

    public ResultHandler() {}

    @Override
    public UserDataSet handle(ResultSet result) throws SQLException {

        if (result.next()) {
            UserDataSet user = new UserDataSet(result.getString("login"), result.getString("passd"));
            return user;
        }
        else {
            return null;
        }
    }
}
