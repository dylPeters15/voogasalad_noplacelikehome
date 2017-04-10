package backend.player;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Created by th174 on 4/9/2017.
 */
public final class ChatMessage {
	private final ImmutablePlayer sender;
	private final Instant timeStamp;
	private final AccessLevel accessLevel;
	private final String content;

	public ChatMessage(AccessLevel accessLevel, ImmutablePlayer sender, String content) {
		this.sender = sender;
		this.timeStamp = Instant.now(Clock.systemUTC());
		this.accessLevel = accessLevel;
		this.content = content;
	}

	public ImmutablePlayer getSender() {
		return sender;
	}

	public String getContent() {
		return content;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public Instant getTimeStamp() {
		return timeStamp;
	}

	@Override
	public String toString() {
		return String.format("--%s--\n[%s] %s: %s", getTimeStamp().atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM)), getAccessLevel(), getSender().getName(), getContent());
	}

	public enum AccessLevel {
		WHISPER, TEAM, ALL
	}
}
