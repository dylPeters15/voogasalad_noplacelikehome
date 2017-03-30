package backend.unit;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.cell.Cell;
import backend.cell.Terrain;
import backend.game_engine.GameState;
import backend.game_engine.Player;
import backend.unit.properties.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class UnitTemplate extends GameObjectImpl implements Unit {
    private double hitPoints;
    private int movePoints;
    private GridPattern movePattern;
    private Map<String, ActiveAbility<GameObject>> activeAbilities;
    private Map<String, PassiveAbility> passiveAbilties;
    private List<InteractionModifier<Double>> offensiveModifiers;
    private List<InteractionModifier<Double>> defensiveModifiers;
    private Map<Terrain, Integer> moveCosts;
    private Faction faction;

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, new HashMap<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, moveCosts, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<GameObject>> activeAbilities, Collection<PassiveAbility> passiveAbilties, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
        super(unitTemplateName, unitDescription, imgPath);
        this.faction = faction;
        this.moveCosts = new HashMap<>(moveCosts);
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.movePattern = movePattern;
        this.passiveAbilties = passiveAbilties.parallelStream().collect(Collectors.toMap(PassiveAbility::getName, a -> a));
        this.activeAbilities = activeAbilities.parallelStream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
    }

    public UnitInstance createUnit(String unitName, Player ownerPlayer, Cell startingCell, GameState game) {
        return new UnitInstance(this, unitName, getHitPoints(), getMovePoints(), getFaction(), getMovePattern(), getMoveCosts(), getAllActiveAbilities(), getAllPassiveAbilities(), getOffensiveModifiers(), getDefensiveModifiers(), getDescription(), getImgPath(), ownerPlayer, startingCell, game);
    }

    @Override
    public UnitTemplate getUnitType() {
        return this;
    }

    @Override
    public HitPoints getHitPoints() {
        return new HitPoints(hitPoints);
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    @Override
    public MovePoints getMovePoints() {
        return new MovePoints(movePoints);
    }

    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    @Override
    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public GridPattern getMovePattern() {
        return movePattern;
    }

    public void setMovePattern(GridPattern movePattern) {
        this.movePattern = movePattern;
    }

    @Override
    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    @Override
    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    @Override
    public Map<String, ActiveAbility<GameObject>> getActiveAbilities() {
        return activeAbilities;
    }

    @Override
    public Map<String, PassiveAbility> getPassiveAbilities() {
        return passiveAbilties;
    }

    @Override
    public Map<Terrain, Integer> getMoveCosts() {
        return moveCosts;
    }
}
