import util.net.Server;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class VoogaServerMain {
    public static void main(String[] args) throws Exception {
        Server<SimpleChatLogTest> voogaServer = new Server<>(
                new SimpleChatLogTest(),
                10023,
                SimpleChatLogTest::toString,
                obj -> (SimpleChatLogTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        voogaServer.start();
        while (!voogaServer.isActive()) {
            Thread.sleep(1000);
            System.out.println("No clients yet.");
        }
        for (int i = 0; voogaServer.isActive(); i++) {
            System.out.println(voogaServer.getState());
//            if (i % 10 == 0) {
//                voogaServer.send(new NetworkingTest("State reset"));
//            }
            Thread.sleep(2000);
        }
    }
}
