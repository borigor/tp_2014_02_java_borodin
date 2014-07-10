package messageSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by igor on 5/31/14.
 */
public class MessageSystem {

    private AddressService addressService = new AddressService();
    private Map<Address, ConcurrentLinkedQueue<Msg>> messages
            = new HashMap<Address, ConcurrentLinkedQueue<Msg>>();

    public void sendMessage(Msg message) {
        Queue<Msg> messageQueue = messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = messages.get(abonent.getAddress());
        if (messageQueue == null)
            return;
        while (!messageQueue.isEmpty()) {
            Msg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    public void addService(Abonent abonent) {
        messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    public AddressService getAddressService() {
        return addressService;
    }
}
