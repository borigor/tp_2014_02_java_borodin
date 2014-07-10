package messageSystem;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by igor on 5/31/14.
 */
public class Address {

    private static AtomicInteger abonentIdCreator = new AtomicInteger();
    private final int abonentId;

    public Address() {
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode() {
        return abonentId;
    }
}
