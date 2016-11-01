package beauj.workshop04.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;

@ApplicationScoped
public class ChessGames {

	private final Lock lock = new ReentrantLock();
	private final Map<String, List<Session>> games;

	public ChessGames() {
		games = new HashMap<>();
	}

	public List<Session> createGame(String gameId) {
		return (mutex(() -> games.computeIfAbsent(gameId, g -> new LinkedList<>())));
	}

	public void addPlayer(String gameId, Session session) {
		mutex(() -> { 
			createGame(gameId).add(session);
		});
	}

	public List<Session> getGame(String gameId) {
		return (mutex(() -> {
			if (games.containsKey(gameId))
				return (Collections.unmodifiableList(games.get(gameId)));
			return (Collections.emptyList());
		}));
	}

	public <T> T mutex(final Callable<T> c) {
		lock.lock();
		try {
			return (c.call());
		} catch (Exception ex) {
			return (null);
		} finally { lock.unlock(); }
	}

	public void mutex(final Runnable r) {
		lock.lock();
		try {
			r.run();
		} finally { lock.unlock(); }
	}

}
