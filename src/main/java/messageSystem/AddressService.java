package messageSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by igor on 5/31/14.
 */
public class AddressService {

    private int currentService = 0;
    private int size = 0;

    Map<Integer, Address> idToAddress = new HashMap<Integer, Address>();

    public synchronized Address getAccountService() {

        return idToAddress.get((currentService++)%size);
    }

    public synchronized void setAccountService(Address accountService) {

        idToAddress.put(size++, accountService);
    }
}
