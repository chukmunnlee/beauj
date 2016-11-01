package beauj.workshop04.web;

import beauj.workshop04.model.ChessGames;
import java.io.IOException;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/chess-event/{gid}")
public class ChessSocket {

	@Resource(lookup="concurrent/myFeedPool")
	private ManagedScheduledExecutorService service;

	@Inject private ChessGames chessGames;

	private String gameId;

	@OnOpen
	public void onOpen(Session sess, @PathParam("gid")String gid) {
		gameId = gid;
		chessGames.addPlayer(gameId, sess);
	}

	@OnMessage
	public void onMessage(final String move) {
		service.execute(() -> {
			chessGames.mutex(() -> {
				chessGames.getGame(gameId).stream()
						.forEach(s -> {
							try {
								s.getBasicRemote().sendText(move);
							} catch (IOException ex) {
								try { s.close(); } catch (IOException e) { }
							}
						});
			});
		});
	}
}
