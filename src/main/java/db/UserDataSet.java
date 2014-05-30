package db;

/**
 * Created by igor on 5/21/14.
 */
public class UserDataSet {
    private Long id;
    private String login;
    private String passd;

    public UserDataSet(Long id, String login, String passd) {
        this.id = id;
        this.login = login;
        this.passd = passd;
    }

    public UserDataSet(String login, String passd) {
        this.login = login;
        this.passd = passd;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() { return login; }

    public String getPassword() {


        return passd;
    }
}
