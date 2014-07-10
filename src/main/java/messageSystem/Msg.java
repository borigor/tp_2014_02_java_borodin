package messageSystem;

/**
 * Created by igor on 5/31/14.
 */
public abstract class Msg {

    final private Address from;
    final private Address to;

    public Msg(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Abonent abonent);
}
