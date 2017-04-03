package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.unit.properties.TriggeredAbility;
import backend.unit.properties.TriggeredEffect;
import backend.util.ImmutableGameState.Event;
import backend.util.VoogaObject;

import java.util.Collection;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class CellEffect extends TriggeredEffect {
    //TODO: ResourceBundlify
    public static final CellEffect FULL_HEAL = new CellEffect("Full Heal", (occupant, event, game) -> occupant.getHitPoints().resetValue(), "Units occupying this cell have their HP fully restored at the start of each turn.", "Red_Cross.png", Event.TURN_START);
    public static final CellEffect POISON = new CellEffect("Poison", (occupant, event, game) -> occupant.addTriggeredAbility(TriggeredAbility.POISONED), "Units occupying this cell are poisoned at the end of the turn.", "Drink_bleach.png", Event.TURN_END);
    public static final CellEffect STRONG_ATTACK = new CellEffect("Strong Attack", (occupant, event, game) -> occupant.addOffensiveModifier(InteractionModifier.STRONG_ATTACK), "Units occupying this cell are poisoned at the end of the turn.", "Drink_bleach.png", Event.TURN_END);

    public CellEffect(String name, Effect effect, String description, String imgPath, Event... activationTriggers) {
        super(name, effect, description, imgPath, activationTriggers);
    }

    public CellEffect(String name, Effect effect, String description, String imgPath, Collection<Event> activationTriggers) {
        super(name, effect, description, imgPath, activationTriggers);
    }

    public static Collection<CellEffect> getPredefinedCellEffects() {
        return VoogaObject.getPredefined(CellEffect.class);
    }
}
