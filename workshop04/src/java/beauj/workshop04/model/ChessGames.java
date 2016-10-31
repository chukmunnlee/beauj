package beauj.workshop04.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class ChessGames {

	private final Map<String, List<Session>> games;

	public ChessGames() {
		games = new HashMap<>();
	}

	public List<Session> createGame(String gameId) {
		return (games.computeIfAbsent(gameId, g -> new LinkedList<>()));
	}

	public void addPlayer(String gameId, Session session) {
		createGame(gameId).add(session);
	}

	public List<Session> getGame(String gameId) {
		if (games.containsKey(gameId))
			return (games.get(gameId));
		return (Collections.emptyList());
	}
}
