package messageSystem;

import frontend.Frontend;

/**
 * Created by igor on 5/31/14.
 */
public class MsgUpdateUserId extends MsgToFrontend {

    private String sessionId;
    private Long userId;

    public MsgUpdateUserId(Address from, Address to, String sessionId, Long userId) {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setUserId(sessionId, userId);
    }
}
