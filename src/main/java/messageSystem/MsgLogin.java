package messageSystem;

import db.AccountService;

import java.sql.SQLException;

/**
 * Created by igor on 5/31/14.
 */
public class MsgLogin extends MsgToAS {

    private final String login;
    private final String pass;
    private final String sessionId;

    public MsgLogin(Address from, Address to, String login, String pass, String sessionId) {
        super(from, to);
        this.login = login;
        this.pass = pass;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        try {
            accountService.login(login, pass);
            accountService.getMessageSystem().sendMessage(
                    new MsgUpdateUserId(getTo(), getFrom(), sessionId, accountService.getUserId(login)));
        } catch (SQLException e) {
            e.printStackTrace();
            accountService.getMessageSystem().sendMessage(
                    new MsgUpdateUserId(getTo(), getFrom(), sessionId, (long)-1));
        }
    }
}
