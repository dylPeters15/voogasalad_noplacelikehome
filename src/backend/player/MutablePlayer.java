package backend.player;

public interface MutablePlayer extends ImmutablePlayer {
	void setTeam(Team team);
}
