import util.net.Client;

import java.util.Scanner;

/**
 * @author Created by th174 on 4/1/2017.
 */
public class VoogaClientMain {
    public static final String HOSTNAME = "25.4.129.184";
    public static final String CLEARSCREEN = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    //    public static final String HOSTNAME = Client.LOCALHOST;

    public static void main(String[] args) throws Exception {
        Client<SimpleChatLogTest> voogaClient = new Client<>(
                HOSTNAME,
                10023,
                SimpleChatLogTest::toString,
                obj -> (SimpleChatLogTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        Scanner stdin = new Scanner(System.in);
        voogaClient.addListener(state -> System.out.print(CLEARSCREEN + state.getLast() + "\n\n>>  "));
        voogaClient.start();
        while (voogaClient.isActive()) {
            String input = stdin.nextLine();
            String user = System.getProperty("user.name");
            voogaClient.send(state -> state.appendMessage(input, user));
        }
    }
}
