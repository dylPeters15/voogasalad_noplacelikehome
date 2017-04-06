package backend.unit;

import backend.cell.CellInstance;
import backend.cell.Terrain;
import backend.player.Player;
import backend.unit.properties.*;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class UnitTemplate extends VoogaTemplate {
    public static final UnitTemplate SKELETON_WARRIOR = new UnitTemplate("Skeleton Warrior", 45, 5, Faction.UNDEAD, GridPattern.HEXAGONAL_ADJACENT, "Once a noble knight in service of his kingdom, the skeleton warrior once again takes up the blade for the lich king.", "spooky1.png") {{
        addActiveAbility(ActiveAbility.SWORD);
        addOffensiveModifier(InteractionModifier.CHAOTIC);
    }};
    public static final UnitTemplate SKELETON_ARCHER = new UnitTemplate("Skeleton Archer") {{
        setFaction(Faction.UNDEAD);
        setHitPoints(40);
        setMovePoints(6);
        setMovePattern(GridPattern.HEXAGONAL_ADJACENT);
        setDescription("The skeletal corpse of an impoverished serf left to starve, reanimated by necromancy. Now, bow and arrow in hand, he enacts his revenge on the living.");
        addOffensiveModifier(InteractionModifier.CHAOTIC);
        addActiveAbility(ActiveAbility.BOW);
    }};
    private final Map<String, ActiveAbility> activeAbilities;
    private final Map<String, TriggeredEffectTemplate> triggeredAbilities;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> terrainMoveCosts;
    private double hitPoints;
    private int movePoints;
    private GridPattern movePattern;
    private Faction faction;

    public UnitTemplate(String unitTemplateName) {
        this(unitTemplateName, 0, 0, null, null, null, "", "");
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, new HashMap<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, hitPoints, movePoints, faction, movePattern, moveCosts, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, double hitPoints, int movePoints, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility> activeAbilities, Collection<TriggeredEffectTemplate> triggeredAbilities, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
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

    public void addTriggeredAbility(TriggeredEffectTemplate instance) {
        triggeredAbilities.put(instance.getName(), instance);
    }

    public void removeTriggeredAbility(TriggeredEffectTemplate instance) {
        triggeredAbilities.remove(instance.getName());
    }

    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    public void addOffensiveModifier(InteractionModifier<Double> modifier) {
        offensiveModifiers.add(modifier);
    }

    public void removeOffensiveModidier(InteractionModifier<Double> modifier) {
        defensiveModifiers.remove(modifier);
    }

    public Map<String, ActiveAbility> getActiveAbilities() {
        return activeAbilities;
    }

    public void addActiveAbility(ActiveAbility activeAbility) {
        activeAbilities.put(activeAbility.getName(), activeAbility);
    }

    public void remoteActiveAbility(ActiveAbility activeAbility) {
        activeAbilities.remove(activeAbility.getName(), activeAbility);
    }

    public Map<String, TriggeredEffectTemplate> getTriggeredAbilities() {
        return triggeredAbilities;
    }

    public Map<Terrain, Integer> getTerrainMoveCosts() {
        return terrainMoveCosts;
    }
}