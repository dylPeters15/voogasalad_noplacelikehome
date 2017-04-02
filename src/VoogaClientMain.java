import util.net.VoogaClient;
import util.net.VoogaRequest;

import java.util.Scanner;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static void main(String[] args) throws Exception {
        VoogaClient<NetworkingTest> voogaClient = new VoogaClient<>("localhost", 10023, obj -> (NetworkingTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        Scanner stdin = new Scanner(System.in);
        while (voogaClient.isActive()) {
            String input = stdin.nextLine();
            voogaClient.sendRequest(new VoogaRequest<NetworkingTest>(state -> {
                state.set(state.get() + input + " ");
                return state;
            }));
            System.out.println(voogaClient.getState());
        }
    }
}
