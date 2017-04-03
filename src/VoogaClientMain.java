import util.net.Client;

import java.util.Scanner;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static void main(String[] args) throws Exception {
        Client<NetworkingTest> voogaClient = new Client<>(
                "10.190.53.132",
                10023,
                NetworkingTest::toString,
                obj -> (NetworkingTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        Scanner stdin = new Scanner(System.in);
        while (voogaClient.isActive()) {
            String input = stdin.nextLine();
            voogaClient.send(state -> {
                state.set(state.get() + input + " ");
                return state;
            });
            System.out.println(voogaClient.getState());
        }
    }
}
