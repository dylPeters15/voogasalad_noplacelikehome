import util.net.VoogaClient;
import util.net.VoogaRequest;

import java.io.IOException;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static void main(String[] args) throws Exception {
        VoogaClient<NetworkingTest> voogaClient = new VoogaClient<>("localhost", 10023, obj -> (NetworkingTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        while (voogaClient.sendRequest(new VoogaRequest<>(state -> state.set(state.get() + 1)))) {
            Thread.sleep(5000);
        }
    }
}
