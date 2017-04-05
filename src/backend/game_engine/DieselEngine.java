package backend.game_engine;

import backend.util.MutableGameState;
import util.net.ObservableServer;

import java.util.function.Consumer;

public class DieselEngine implements GameEngine {

    private ObservableServer<MutableGameState> server;
    private Consumer<MutableGameState> stateUpdateListener = this::checkRules;

    public DieselEngine(ObservableServer<MutableGameState> s) {
        server = s;
        //server.addListener(stateUpdateListener);
        server.run();
    }

    private void checkRules(MutableGameState state) {

        return;
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub

    }

    @Override
    public void save() {
        // TODO Auto-generated method stub

    }

    @Override
    public void load() {
        // TODO Auto-generated method stub
    }

    @Override
    public String toXML() {
        // TODO Auto-generated method stub
        return null;
    }
}