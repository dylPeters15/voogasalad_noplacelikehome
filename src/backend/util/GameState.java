package backend.util;

import backend.game_engine.ResultQuadPredicate;
import backend.grid.MutableGrid;
import backend.io.XMLSerializable;
import backend.player.Player;
import backend.player.Team;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * @author Created by th174 on 3/30/2017.
 */

public abstract class GameState implements XMLSerializable, MutableGameState {
    private Collection<ResultQuadPredicate> currentObjectives;
    private Map<Event, List<BiConsumer<Player, ImmutableGameState>>> turnActions;
    private Optional<List<Predicate<Player>>> turnRequirements;

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public Player getCurrentPlayer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Team> getTeams() {
        return null;
    }


    @Override
    public MutableGrid getGrid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getTurnNumber() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void addObjective(ResultQuadPredicate winCondition) {
        currentObjectives.add(winCondition);
    }

    @Override
    public void addTurnRequirement(BiPredicate<Player, ImmutableGameState> requirement) {
    }

    @Override
    public void addEventHandler(BiConsumer<Player, ImmutableGameState> eventListener, Event event) {
        turnActions.merge(event, new ArrayList<>(Collections.singletonList(eventListener)), (list, t) -> {
            list.addAll(t);
            return list;
        });
    }

    @Override
    public String toXML() {
        // TODO Auto-generated method stub
        return null;
    }

}
