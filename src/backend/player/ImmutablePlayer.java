package backend.player;

import backend.cell.CellInstance;
import backend.grid.ModifiableGameBoard;
import backend.unit.UnitInstance;
import backend.unit.properties.Faction;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.List;

public interface ImmutablePlayer extends VoogaObject {
	Team getTeam();

	Faction getFaction();

	Collection<UnitInstance> getOwnedUnits(ModifiableGameBoard grid);

	Collection<CellInstance> getVisibleCells();

	Collection<CellInstance> getExploredCells();

	List<ChatMessage> getChatLog();

	void receiveMessage(ChatMessage message);
}
