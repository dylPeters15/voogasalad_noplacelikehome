package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.util.VoogaEntity;

/**
 * @author Created by th174 on 4/8/2017.
 */
public interface Terrain extends VoogaEntity {
	@Override
	ModifiableTerrain copy();

	int getDefaultMoveCost();

	InteractionModifier<Double> getDefaultDefenseModifier();
}
