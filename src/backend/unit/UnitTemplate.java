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
public class UnitTemplate extends VoogaTemplate<UnitTemplate> {
    //TODO ResourceBundlify
    public static final UnitTemplate SKELETON_WARRIOR = new UnitTemplate("Skeleton Warrior")
            .setFaction(Faction.UNDEAD)
            .addUnitStat(UnitStatTemplate.HITPOINTS.clone().setMaxValue(39.0))
            .addUnitStat(UnitStatTemplate.MOVEPOINTS.clone().setMaxValue(5))
            .setDescription("Once a noble knight in service of his kingdom, the skeleton warrior once again takes up the blade for the lich king.")
            .setImgPath("spooky1.png")
            .setMovePattern(GridPattern.HEXAGONAL_ADJACENT)
            .addActiveAbility(ActiveAbility.SWORD)
            .addOffensiveModifier(InteractionModifier.CHAOTIC);
    public static final UnitTemplate SKELETON_ARCHER = new UnitTemplate("Skeleton Archer")
            .setFaction(Faction.UNDEAD)
            .addUnitStat(UnitStatTemplate.HITPOINTS.clone().setMaxValue(34.0))
            .addUnitStat(UnitStatTemplate.MOVEPOINTS.clone().setMaxValue(6))
            .setMovePattern(GridPattern.HEXAGONAL_ADJACENT)
            .setImgPath("spooky2.png")
            .setDescription("The skeletal corpse of an impoverished serf left to starve, reanimated by necromancy. Now, bow and arrow in hand, he enacts his revenge on the living.")
            .addOffensiveModifier(InteractionModifier.CHAOTIC)
            .addActiveAbility(ActiveAbility.BOW);
    private final Map<String, ActiveAbility> activeAbilities;
    private final Map<String, TriggeredEffectTemplate> triggeredAbilities;
    private final List<InteractionModifier<Double>> offensiveModifiers;
    private final List<InteractionModifier<Double>> defensiveModifiers;
    private final Map<Terrain, Integer> terrainMoveCosts;
    private final Map<String, UnitStatTemplate> stats;
    private GridPattern movePattern;
    private Faction faction;

    public UnitTemplate(String unitTemplateName) {
        this(unitTemplateName, Collections.emptySet(), null, null, null, "", "");
    }

    public UnitTemplate(String unitTemplateName, Collection<UnitStatTemplate> unitStats, Faction faction, GridPattern movePattern, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, unitStats, faction, movePattern, new HashMap<>(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, Collection<UnitStatTemplate> unitStats, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, String unitTemplateDescription, String imgPath) {
        this(unitTemplateName, unitStats, faction, movePattern, moveCosts, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), unitTemplateDescription, imgPath);
    }

    public UnitTemplate(String unitTemplateName, Collection<UnitStatTemplate> unitStats, Faction faction, GridPattern movePattern, Map<Terrain, Integer> moveCosts, Collection<ActiveAbility> activeAbilities, Collection<TriggeredEffectTemplate> triggeredAbilities, Collection<InteractionModifier<Double>> offensiveModifiers, Collection<InteractionModifier<Double>> defensiveModifiers, String unitDescription, String imgPath) {
        super(unitTemplateName, unitDescription, imgPath);
        this.faction = faction;
        this.terrainMoveCosts = new HashMap<>(moveCosts);
        this.stats = unitStats.parallelStream().collect(Collectors.toMap(UnitStatTemplate::getName, e -> e));
        this.movePattern = movePattern;
        this.triggeredAbilities = triggeredAbilities.parallelStream().collect(Collectors.toMap(TriggeredEffectTemplate::getName, e -> e));
        this.activeAbilities = activeAbilities.parallelStream().collect(Collectors.toMap(ActiveAbility::getName, e -> e));
        this.offensiveModifiers = new ArrayList<>(offensiveModifiers);
        this.defensiveModifiers = new ArrayList<>(defensiveModifiers);
    }

    @Override
    public UnitTemplate clone() {
        return new UnitTemplate(getName(), getUnitStats(), getFaction(), getMovePattern(), getTerrainMoveCosts(), getActiveAbilities(), getTriggeredAbilities(), getOffensiveModifiers(), getDefensiveModifiers(), getDescription(), getImgPath());
    }

    public static Collection<UnitTemplate> getPredefinedUnitTemplates() {
        return getPredefined(UnitTemplate.class);
    }

    public UnitInstance createInstance(String unitName, Player ownerPlayer, CellInstance startingCell) {
        return new UnitInstance(unitName, this, ownerPlayer, startingCell);
    }

    public Collection<UnitStatTemplate> getUnitStats() {
        return stats.values();
    }

    public UnitTemplate addUnitStat(UnitStatTemplate unitStat) {
        stats.put(unitStat.getName(), unitStat);
        return this;
    }

    public UnitTemplate removeUnitStat(UnitStatTemplate unitStat) {
        stats.remove(unitStat.getName());
        return this;
    }

    public Faction getFaction() {
        return faction;
    }

    public UnitTemplate setFaction(Faction faction) {
        this.faction = faction;
        return this;
    }

    public GridPattern getMovePattern() {
        return movePattern;
    }

    public UnitTemplate setMovePattern(GridPattern movePattern) {
        this.movePattern = movePattern;
        return this;
    }

    public List<InteractionModifier<Double>> getDefensiveModifiers() {
        return defensiveModifiers;
    }

    public UnitTemplate addTriggeredAbility(TriggeredEffectTemplate instance) {
        triggeredAbilities.put(instance.getName(), instance);
        return this;
    }

    public UnitTemplate removeTriggeredAbility(TriggeredEffectTemplate instance) {
        triggeredAbilities.remove(instance.getName());
        return this;
    }

    public List<InteractionModifier<Double>> getOffensiveModifiers() {
        return offensiveModifiers;
    }

    public UnitTemplate addOffensiveModifier(InteractionModifier<Double> modifier) {
        offensiveModifiers.add(modifier);
        return this;
    }

    public UnitTemplate removeOffensiveModidier(InteractionModifier<Double> modifier) {
        defensiveModifiers.remove(modifier);
        return this;
    }

    public Collection<ActiveAbility> getActiveAbilities() {
        return Collections.unmodifiableCollection(activeAbilities.values());
    }

    public UnitTemplate addActiveAbility(ActiveAbility activeAbility) {
        activeAbilities.put(activeAbility.getName(), activeAbility);
        return this;
    }

    public UnitTemplate remoteActiveAbility(ActiveAbility activeAbility) {
        activeAbilities.remove(activeAbility.getName(), activeAbility);
        return this;
    }

    public Collection<TriggeredEffectTemplate> getTriggeredAbilities() {
        return Collections.unmodifiableCollection(triggeredAbilities.values());
    }

    public Map<Terrain, Integer> getTerrainMoveCosts() {
        return terrainMoveCosts;
    }
}