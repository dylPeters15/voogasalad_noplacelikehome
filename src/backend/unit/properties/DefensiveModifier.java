package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.UnitInstance;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
@FunctionalInterface
public interface DefensiveModifier extends InteractionModifier.Modifier<Double> {
    //Invulnerability
    DefensiveModifier INVULNERABILITY = (incomingDamage, agent, target, game) -> 0.0;
    //Reduced damage when standing next to allied units of the same type
    DefensiveModifier FORMATION = (incomingDamage, agent, target, game) -> incomingDamage *
            (target.getNeighboringUnits().values().parallelStream().flatMap(Collection::stream).parallel().anyMatch(e -> e.getOwner().equals(target.getOwner()) && e.getName().equals(target.getName())) ? .6 : 1);
    //Extra evasion, but takes extra damage
    DefensiveModifier EVASIVE = (incomingDamage, agent, target, game) -> incomingDamage * Math.random() < .5 ? 0 : 1.5;
    //Extra defense when unit doesn't move
    DefensiveModifier STALWART = (incomingDamage, agent, target, game) -> incomingDamage * (target.getMovePoints().isFull() ? .5 : 1);
    //Incoming damage is capped at 5
    DefensiveModifier HARDENED_SHIELDS = (incomingDamage, agent, target, game) -> incomingDamage > 5 ? 5 : incomingDamage;
    //Takes extra damage at night
    DefensiveModifier FEARFUL = (incomingDamage, agent, target, game) -> incomingDamage * (game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5 ? 1.25 : 1);
    //This one is interesting: reflects half damage back to attacker
    DefensiveModifier THORNS = (incomingDamage, agent, target, game) -> {
        agent.getHitPoints().takeDamage(incomingDamage / 2);
        return incomingDamage;
    };

    @Override
    Double modify(Double incomingDamage, UnitInstance agent, UnitInstance target, GameState game);
}