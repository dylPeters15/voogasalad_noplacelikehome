import util.io.XMLSerializable;

/**
 * JUST FOR TESTING
 *
 * @author Created by th174 on 4/1/2017.
 */
public class NetworkingTest implements XMLSerializable {
    public static NetworkingTest INITIAL_STATE = new NetworkingTest("5");
    private int testInt;

    public NetworkingTest(String testInt) {
        this.testInt = Integer.parseInt(testInt);
    }

    public NetworkingTest(NetworkingTest t) {
        this.testInt = t.testInt;
    }

    public void set(int testInt) {
        this.testInt = testInt;
    }

    public int get() {
        return testInt;
    }

    @Override
    public String toXml() {
        return getClass().getName() + "=" + testInt;
    }

    @Override
    public String toString() {
        return toXml();
    }
}