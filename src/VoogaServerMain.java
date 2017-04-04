import backend.game_engine.DieselEngine;
import backend.game_engine.GameEngine;
import backend.util.MutableGameState;
import util.io.Serializer;
import util.io.Unserializer;
import util.net.ObservableServer;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaServerMain {
    public static final int PORT = 10023;

    public static void main(String[] args) throws Exception {
        //TODO
        MutableGameState gameState = null;
        Serializer<MutableGameState> xstreamSerializer = null;
        Unserializer<MutableGameState> xstreamUnserializer = null;
        ObservableServer<MutableGameState> voogaServer = new ObservableServer<>(
                gameState,
                PORT,
                xstreamSerializer,
                xstreamUnserializer);
        GameEngine gameEngine = new DieselEngine(voogaServer);
        voogaServer.start();
        System.out.println("Server started successfully...");
    }
}
