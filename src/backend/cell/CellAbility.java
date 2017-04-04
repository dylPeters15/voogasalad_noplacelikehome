package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.unit.properties.TriggeredAbility;
import backend.unit.properties.TriggeredEffect;
import backend.util.Event;
import backend.util.VoogaObject;

import java.util.Collection;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class CellAbility extends TriggeredEffect {
    //TODO: ResourceBundlify
    public static final CellAbility FULL_HEAL = new CellAbility("Full Heal", (occupant, event, game) -> occupant.getHitPoints().resetValue(), "Units occupying this cell have their HP fully restored at the start of each turn.", "Red_Cross.png", Event.TURN_START);
    public static final CellAbility POISON = new CellAbility("Poison", (occupant, event, game) -> occupant.addTriggeredAbility(TriggeredAbility.POISONED), "Units occupying this cell are poisoned at the end of their turn.", "Drink_bleach.png", Event.TURN_END);
    public static final CellAbility ON_FIRE = new CellAbility("On Fire", (occupant, event, game) -> occupant.takeDamage(8), "Units occupying this cell at the start of their turn take 8 damage", "My_mixtape.png", Event.TURN_START);
    public static final CellAbility STRONG_ATTACK = new CellAbility("Strong Attack", (occupant, event, game) -> occupant.addOffensiveModifier(InteractionModifier.STRONG_ATTACK), "Units occupying this cell are poisoned at the end of the turn.", "Drink_bleach.png", Event.TURN_END);

    public CellAbility(String name, Effect effect, String description, String imgPath, Event... activationTriggers) {
        super(name, effect, description, imgPath, activationTriggers);
    }

    public CellAbility(String name, Effect effect, String description, String imgPath, Collection<Event> activationTriggers) {
        super(name, effect, description, imgPath, activationTriggers);
    }

    public static Collection<CellAbility> getPredefinedCellEffects() {
        return VoogaObject.getPredefined(CellAbility.class);
    }
}
