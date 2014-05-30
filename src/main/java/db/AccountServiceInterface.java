package db;

import java.sql.SQLException;

/**
 * Created by igor on 5/23/14.
 */
public interface AccountServiceInterface {
    DBStatus registration(String login, String passd) throws SQLException;
    DBStatus login(String login, String passd) throws SQLException;
}
