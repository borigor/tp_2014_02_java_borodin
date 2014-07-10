package messageSystem;

import db.AccountService;

/**
 * Created by igor on 5/31/14.
 */
public abstract class MsgToAS extends Msg {

    public MsgToAS(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService)abonent);
        }
    }

    abstract void exec(AccountService accountService);
}
