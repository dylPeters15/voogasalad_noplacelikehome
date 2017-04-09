package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.util.VoogaObject;

/**
 * @author Created by th174 on 4/8/2017.
 */
public interface TerrainInstance extends VoogaObject {
	int getDefaultMoveCost();

	InteractionModifier<Double> getDefaultDefenseModifier();
}
