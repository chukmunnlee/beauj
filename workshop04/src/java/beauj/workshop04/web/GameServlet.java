package beauj.workshop04.web;

import beauj.workshop04.model.ChessGames;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.inject.Inject;
import javax.json.Json;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

	@Inject private ChessGames chessGames; 

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String name = req.getParameter("name");
		String gameId = UUID.randomUUID().toString().substring(0, 8);

		chessGames.createGame(gameId);

		resp.setStatus(HttpServletResponse.SC_CREATED);
		resp.setContentType(MediaType.APPLICATION_JSON);

		try (PrintWriter pw = resp.getWriter()) {
			pw.print(Json.createObjectBuilder()
					.add("gameId", gameId)
					.build().toString());
			pw.flush();
		}
	}

	
	
}
