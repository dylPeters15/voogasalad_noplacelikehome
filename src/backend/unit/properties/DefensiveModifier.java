package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.UnitInstance;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
@FunctionalInterface
interface DefensiveModifier extends InteractionModifier.Modifier<Double> {
    DefensiveModifier INVULNERABILITY = (incomingDamage, agent, target, game) -> 0.0;
    DefensiveModifier FORMATION = (incomingDamage, agent, target, game) -> incomingDamage * (target.getNeighboringUnits().values().parallelStream().flatMap(Collection::stream).parallel().anyMatch(e -> e.getOwner().equals(target.getOwner()) && e.getName().equals(target.getName())) ? .6 : 1);
    DefensiveModifier EVASIVE = (incomingDamage, agent, target, game) -> incomingDamage * Math.random() < .5 ? 0 : 1.5;
    DefensiveModifier STALWART = (incomingDamage, agent, target, game) -> incomingDamage * (target.getMovePoints().isFull() ? .5 : 1);
    DefensiveModifier HARDENED_SHIELDS = (incomingDamage, agent, target, game) -> incomingDamage > 5 ? 5 : incomingDamage;
    DefensiveModifier FEARFUL = (incomingDamage, agent, target, game) -> incomingDamage * (game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5 ? 1.25 : 1);
    DefensiveModifier THORNS = (incomingDamage, agent, target, game) -> {
        agent.getHitPoints().takeDamage(incomingDamage / 2);
        return incomingDamage;
    };

    @Override
    Double modify(Double incomingDamage, UnitInstance agent, UnitInstance target, GameState game);
}