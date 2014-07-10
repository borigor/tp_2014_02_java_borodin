package frontend;

import messageSystem.Address;
import messageSystem.AddressService;

/**
 * Created by igor on 5/31/14.
 */
public class UserSession {

    private Long userId;
    private String login;
    private String sessionId;
    private Address accountService;
    private String status;

    public UserSession(String sessionId, String login, AddressService addressService) {
        this.sessionId = sessionId;
        this.login = login;
        this.accountService = addressService.getAccountService();
        this.status = "";
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Address getAccountService() {
        return accountService;
    }

    public String getStatus() { return status; }
}
