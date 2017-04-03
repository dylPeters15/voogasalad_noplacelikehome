import util.net.Client;

import java.util.Scanner;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static final String HOSTNAME = "25.13.30.96";
    //    public static final String HOSTNAME = Client.LOCALHOST;

    public static void main(String[] args) throws Exception {
        Client<SimpleChatLogTest> voogaClient = new Client<>(
                HOSTNAME,
                10023,
                SimpleChatLogTest::toString,
                obj -> (SimpleChatLogTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        Scanner stdin = new Scanner(System.in);
        voogaClient.addListener(state -> System.out.print(state.getLast() + "\n\n>>  "));
        voogaClient.start();
        while (voogaClient.isActive()) {
            String input = stdin.nextLine();
            voogaClient.send(state -> state.appendMessage(input, System.getProperty("user.name")));
        }
    }
}
