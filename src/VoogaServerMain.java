import util.net.Server;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class VoogaServerMain {
    public static void main(String[] args) throws Exception {
        Server<NetworkingTest> voogaServer = new Server<>(
                new NetworkingTest(NetworkingTest.INITIAL_STATE),
                10023,
                NetworkingTest::toString,
                obj -> (NetworkingTest) Class.forName(obj.toString().split("=")[0]).getConstructor(String.class).newInstance(obj.toString().split("=")[1]));
        voogaServer.beginListening();
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
