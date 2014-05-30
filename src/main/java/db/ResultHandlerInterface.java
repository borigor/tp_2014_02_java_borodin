package db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by igor on 5/23/14.
 */
public interface ResultHandlerInterface {
    public UserDataSet handle(ResultSet result) throws SQLException;
}
