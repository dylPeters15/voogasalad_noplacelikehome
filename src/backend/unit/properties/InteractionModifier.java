package backend.unit.properties;

import backend.GameObjectImpl;
import backend.game_engine.GameState;
import backend.unit.Unit;

import java.util.List;

/**
 * Timmy
 *
 * @author Created by th174 on 3/28/2017.
 */
public class InteractionModifier<T> extends GameObjectImpl {
    //TODO: ResourceBundle all this shit
    public static final InteractionModifier<?> NO_EFFECT = new InteractionModifier<>("No effect", (originalValue, agent, target, game) -> originalValue, "Literally does nothing", "Nothing.png");
    //Offensive modifiers, can go on units or attacks
    public static final InteractionModifier<Double> CHAOTIC = new InteractionModifier<>("Chaotic", OffensiveModifier.CHAOTIC, "Attacks do more damage in nighttime, but less damage in daytime.", "Chaotic.png");
    public static final InteractionModifier<Double> LAWFUL = new InteractionModifier<>("Lawful", OffensiveModifier.LAWFUL, "Attacks do more damage in daytime, but less damage in nightime", "Lawful.png");
    public static final InteractionModifier<Double> BLINDED = new InteractionModifier<>("Blinded", OffensiveModifier.BLINDED, "Attacks have a high chance to miss", "Helen_Keller.png");
    public static final InteractionModifier<Double> FIRST_BLOOD = new InteractionModifier<>("First Blood", OffensiveModifier.FIRST_BLOOD, "Attacks do extra damage to targets at full HP.", "First_blood.png");
    public static final InteractionModifier<Double> EXECUTIONER = new InteractionModifier<>("Executioner", OffensiveModifier.EXECUTIONER, "Attacks do extra damage to targets at low HP.", "Axe.png");
    public static final InteractionModifier<Double> BRAVERY = new InteractionModifier<>("Weakened", OffensiveModifier.BRAVERY, "Attacks do extra damage if the defender has more HP than the attacker.", "David&Goliath.png");
    public static final InteractionModifier<Double> ASSASSIN = new InteractionModifier<>("Assassin", OffensiveModifier.ASSASSIN, "Attacks do extra damage to isolated units with no nearby allies", "Zabaniya.png");
    //Defensive modifiers, can go on units only
    public static final InteractionModifier<Double> INVULNERABILITY = new InteractionModifier<>("Invulnerability", DefensiveModifier.INVULNERABILITY, "This unit does not take damage.", "God.png");
    public static final InteractionModifier<Double> FORMATION = new InteractionModifier<>("Formation", DefensiveModifier.FORMATION, "This unit takes less damage when near an allied unit of the same type.", "Phalanx.png");
    public static final InteractionModifier<Double> EVASIVE = new InteractionModifier<>("Evasive", DefensiveModifier.EVASIVE, "This unit has a high chance to evade attacks, but it takes extra damage when hit", "Evasion.png");
    public static final InteractionModifier<Double> STALWART = new InteractionModifier<>("Stalwart", DefensiveModifier.STALWART, "This unit takes less damage if it does not move this turn.", "Siege_Engine.png");
    public static final InteractionModifier<Double> HARDENED_SHIELDS = new InteractionModifier<>("Hardened Shields", DefensiveModifier.HARDENED_SHIELDS, "Incoming attacks that would deal more than 5 damage have their damage reduced to 5", "Protoss_Immortal.png");
    public static final InteractionModifier<Double> FEARFUL = new InteractionModifier<>("Fearful", DefensiveModifier.FEARFUL, "This unit take extra damage in night time", "Scarecrow.png");
    public static final InteractionModifier<Double> THORNS = new InteractionModifier<>("Thorns", DefensiveModifier.THORNS, "This unit reflects half the damage it takes back to the attacker", "Blademail.png");

    private Modifier<T> modifier;

    public InteractionModifier(String name, Modifier<T> modifier, String description, String imgPath) {
        super(name, description, imgPath);
        this.modifier = modifier;
    }

    public T modify(T originalValue, Unit agent, Unit target, GameState game) {
        return modifier.modify(originalValue, agent, target, game);
    }

    static <T> T modifyAll(List<? extends InteractionModifier<T>> modifiers, T originalValue, Unit agent, Unit target, GameState game) {
        for (InteractionModifier<T> op : modifiers) {
            originalValue = op.modify(originalValue, agent, target, game);
        }
        return originalValue;
    }

    public interface Modifier<T> {
        T modify(T originalValue, Unit agent, Unit target, GameState game);
    }
}