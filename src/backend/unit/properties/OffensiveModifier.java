package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.UnitInstance;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
@FunctionalInterface
public interface OffensiveModifier extends InteractionModifier.Modifier<Double> {
    //Bonus damage at night, less during daytime
    OffensiveModifier CHAOTIC = (outgoingDamage, agent, target, game) -> outgoingDamage * (game.getTurnNumber() % 6 == 1 || game.getTurnNumber() % 6 == 2 ? 0.75 : game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5 ? 1.25 : 1);
    //Bonus damage in day, less during nightime
    OffensiveModifier LAWFUL = (outgoingDamage, agent, target, game) -> outgoingDamage * (game.getTurnNumber() % 6 == 1 || game.getTurnNumber() % 6 == 2 ? 1.25 : game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5 ? 0.75 : 1);
    //50% chance to miss
    OffensiveModifier BLINDED = (outgoingDamage, agent, target, game) -> Math.random() < .5 ? outgoingDamage : 0;
    //Extra damage to units with full HP
    OffensiveModifier FIRST_BLOOD = (outgoingDamage, agent, target, game) -> outgoingDamage * (agent.getHitPoints().isFull() ? 1.5 : 1);
    //Extra damage to units with low HP
    OffensiveModifier EXECUTIONER = (outgoingDamage, agent, target, game) -> outgoingDamage * (agent.getHitPoints().getCurrentValue() / agent.getHitPoints().getInitialValue() < .25 ? 2 : 1);
    //Extra damage to units with more hp that attacker
    OffensiveModifier BRAVERY = (outgoingDamage, agent, target, game) -> outgoingDamage * (target.getHitPoints().getCurrentValue() > agent.getHitPoints().getCurrentValue() ? 1.5 : 1);
    //This one is pretty cool. 50% extra damage to units that aren't standing near a teammate
    OffensiveModifier ASSASSIN = (outgoingDamage, agent, target, game) -> outgoingDamage *
            (target.getNeighboringUnits().values().stream().flatMap(Collection::stream).anyMatch(e -> e.getOwner().equals(target.getOwner())) ? 1 : 1.5);

    @Override
    Double modify(Double outgoingDamage, UnitInstance agent, UnitInstance target, GameState game);
}
