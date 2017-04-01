package backend.unit;

import backend.cell.CellInstance;
import backend.cell.Terrain;
import backend.util.GameState;
import backend.player.Player;
import backend.unit.properties.*;
import backend.util.VoogaInstance;
import backend.util.VoogaObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class UnitTemplate extends VoogaObject implements Unit {
    private double hitPoints;
    private int movePoints;
    private GridPattern movePattern;
    private Map<String, ActiveAbility<VoogaInstance>> activeAbilities;
    private Map<String, TriggeredAbility> triggeredAbilities;
    private List<InteractionModifier<Double>> offensiveModifiers;
    private List<InteractionModifier<Double>> defensiveModifiers;
    private Map<Terrain, Integer> terrainMoveCosts;
    private Faction faction;

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, new HashMap<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, moveCosts, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, Collections.EMPTY_SET, unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<VoogaInstance>> activeAbilities, Collection<TriggeredAbility> triggeredAbilities, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
        super(unitTemplateName, unitDescription, imgPath);
        this.faction = faction;
        this.terrainMoveCosts = new HashMap<>(moveCosts);
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.movePattern = movePattern;
        this.triggeredAbilities = triggeredAbilities.parallelStream().collect(Collectors.toMap(TriggeredAbility::getName, a -> a));
        this.activeAbilities = activeAbilities.parallelStream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
    }

    public UnitInstance createInstance(String unitName, Player ownerPlayer, CellInstance startingCell, GameState game) {
        return new UnitInstance(unitName, this, ownerPlayer, startingCell, game);
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
    public Map<String, ActiveAbility<VoogaInstance>> getActiveAbilities() {
        return activeAbilities;
    }

    @Override
    public Map<String, TriggeredAbility> getTriggeredAbilities() {
        return triggeredAbilities;
    }

    @Override
    public Map<Terrain, Integer> getTerrainMoveCosts() {
        return terrainMoveCosts;
    }

    public static Collection<UnitTemplate> getPredefinedUnitTemplates() {
        return getPredefined(UnitTemplate.class);
    }
}
