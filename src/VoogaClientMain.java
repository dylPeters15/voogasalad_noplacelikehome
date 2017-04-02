import util.io.XMLSerializable;
import util.net.VoogaClient;
import util.net.VoogaRequest;

import java.io.IOException;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static void main(String[] args) throws IOException, InterruptedException, XMLSerializable.XMLException {
        VoogaClient<NetworkingTest> voogaClient = new VoogaClient<>("localhost", 10023);
        while (voogaClient.sendRequest(new VoogaRequest<>(state -> state.set(state.get() + 1)))) {
            Thread.sleep(5000);
        }
    }
}
