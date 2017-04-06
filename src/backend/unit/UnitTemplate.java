package backend.unit;

import backend.cell.CellInstance;
import backend.cell.Terrain;
import backend.player.Player;
import backend.unit.properties.*;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaObject;
import backend.util.VoogaTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class UnitTemplate extends VoogaTemplate {
    private final Map<String, ActiveAbility<VoogaObject>> activeAbilities;
    private final Map<String, TriggeredEffectTemplate> triggeredAbilities;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> terrainMoveCosts;
    private double hitPoints;
    private int movePoints;
    private GridPattern movePattern;
    private Faction faction;

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, new HashMap<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, moveCosts, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility<VoogaObject>> activeAbilities, Collection<TriggeredEffectTemplate> triggeredAbilities, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
        super(unitTemplateName, unitDescription, imgPath);
        this.faction = faction;
        this.terrainMoveCosts = new HashMap<>(moveCosts);
        this.hitPoints = hitPoints;
        this.movePoints = movePoints;
        this.movePattern = movePattern;
        this.triggeredAbilities = triggeredAbilities.parallelStream().collect(Collectors.toMap(TriggeredEffectTemplate::getName, a -> a));
        this.activeAbilities = activeAbilities.parallelStream().collect(Collectors.toMap(ActiveAbility::getName, a -> a));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
    }

    public static Collection<UnitTemplate> getPredefinedUnitTemplates() {
        return getPredefined(UnitTemplate.class);
    }

    public UnitInstance createInstance(String unitName, Player ownerPlayer, CellInstance startingCell) {
        return new UnitInstance(unitName, this, ownerPlayer, startingCell);
    }

    public HitPoints getHitPoints() {
        return new HitPoints(hitPoints);
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public MovePoints getMovePoints() {
        return new MovePoints(movePoints);
    }

    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public GridPattern getMovePattern() {
        return movePattern;
    }

    public void setMovePattern(GridPattern movePattern) {
        this.movePattern = movePattern;
    }

    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    public Map<String, ActiveAbility<VoogaObject>> getActiveAbilities() {
        return activeAbilities;
    }

    public Map<String, TriggeredEffectTemplate> getTriggeredAbilities() {
        return triggeredAbilities;
    }

    public Map<Terrain, Integer> getTerrainMoveCosts() {
        return terrainMoveCosts;
    }
}
