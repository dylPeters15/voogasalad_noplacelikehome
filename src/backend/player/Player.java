package backend.player;

import backend.cell.CellInstance;
import backend.grid.ModifiableGameBoard;
import backend.unit.UnitInstance;
import backend.unit.properties.Faction;
import backend.util.VoogaTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Alex
 *
 * @author Created by th174 on 3/28/2017.
 */
public class Player extends VoogaTemplate<Player> implements MutablePlayer {
	private Faction faction;
	private Team team;
	private List<ChatMessage> chatLog;

	public Player(String name, Faction faction, String description, String imgPath) {
		this(name, null, faction, description, imgPath);
	}

	public Player(String name, Team team, Faction faction, String description, String imgPath) {
		super(name, description, imgPath);
		this.faction = faction;
		chatLog = new ArrayList<>();
		this.team = team;
	}

	@Override
	public Player copy() {
		return new Player(getName(), getTeam(), getFaction(), getDescription(), getImgPath());
	}

	@Override
	public Team getTeam() {
		return team;
	}

	@Override
	public Faction getFaction() {
		return faction;
	}

	@Override
	public Collection<UnitInstance> getOwnedUnits(ModifiableGameBoard grid) {
		return grid.getUnits().parallelStream().filter(e -> e.getOwner().equals(this)).collect(Collectors.toSet());
	}

	@Override
	public Collection<CellInstance> getVisibleCells() {
		return null;
	}

	@Override
	public Collection<CellInstance> getExploredCells() {
		return null;
	}

	@Override
	public Player setTeam(Team team) {
		this.team = team;
		return this;
	}

	@Override
	public void receiveMessage(ChatMessage message) {
		chatLog.add(message);
	}

	@Override
	public List<ChatMessage> getChatLog() {
		return Collections.unmodifiableList(chatLog);
	}
}
