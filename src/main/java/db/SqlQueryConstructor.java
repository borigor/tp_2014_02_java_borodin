package db;

/**
 * Created by igor on 5/24/14.
 */
public class SqlQueryConstructor {

    public static String constructUpdate(UserDataSet user) {
        return "INSERT INTO users(login, passd) VALUES ('" + user.getLogin() + "','"
                + user.getPassword() + "');" ;
    }

    public static String constructSelect(String login) {
        return "SELECT * FROM users WHERE login = '" + login + "';";
    }
}
