/**
 * Class used for testing client-server networking
 *
 * @author Created by th174 on 4/1/2017.
 */
public class NetworkingTest {
    public static final NetworkingTest INITIAL_STATE = new NetworkingTest(" ");
    private String test;

    public NetworkingTest(String test) {
        this.test = test;
    }

    public NetworkingTest(NetworkingTest t) {
        this.test = t.test;
    }

    public void set(String test) {
        this.test = test;
    }

    public String get() {
        return test;
    }

    @Override
    public String toString() {
        return getClass().getName() + "=" + test;
    }
}