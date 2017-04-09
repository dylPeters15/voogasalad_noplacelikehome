package backend.player;

public interface MutablePlayer extends ImmutablePlayer {
	Player setTeam(Team team);
}
