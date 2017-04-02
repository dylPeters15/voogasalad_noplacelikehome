import util.net.VoogaServer;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class VoogaServerMain {
    public static void main(String[] args) throws Exception {
        VoogaServer<NetworkingTest> voogaServer = new VoogaServer<>(new NetworkingTest(NetworkingTest.INITIAL_STATE), NetworkingTest::toString, 10023);
        voogaServer.beginListening();
        while (!voogaServer.isActive()) {
            Thread.sleep(1000);
            System.out.println("No clients yet.");
        }
        for (int i = 0; voogaServer.isActive(); i++) {
            System.out.println(voogaServer.getState());
            if (i % 10 == 0) {
                voogaServer.sendNewState(new NetworkingTest("State reset"));
            }
            Thread.sleep(2000);
        }
    }
}
