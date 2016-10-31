package beauj.workshop04.web;

import java.io.IOException;
import javax.enterprise.context.RequestScoped;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/chess-event/{gid}")
public class ChessSocket {

	private String gameId;

	@OnOpen
	public void onOpen(Session sess, @PathParam("gid")String gid) {
		gameId = gid;
	}

	@OnMessage
	public void onMessage(Session sess, String move) {
		for (Session s: sess.getOpenSessions())
			try {
				s.getBasicRemote().sendText(move);
			} catch (IOException ex) {
				try { s.close(); } catch (IOException e) { }
			}
	}
	
}
