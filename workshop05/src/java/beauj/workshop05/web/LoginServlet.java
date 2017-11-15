package beauj.workshop05.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

	private byte[] SECRET = null;
	private final Long ONE_HOUR = 1000L * 60 * 60;

	@Override
	public void init() throws ServletException {

		SECRET = (byte[])getServletContext().getAttribute("secret");

		log("Secret initialized");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		final String username = req.getParameter("username");
		final String password = req.getParameter("password");

		try {
			req.login(username, password);
		} catch (ServletException ex) {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.setContentType(MediaType.APPLICATION_JSON);

		JsonObject token = Json.createObjectBuilder()
				.add("access_token", generateToken(username, req.getContextPath() + req.getServletPath()))
				.add("token_type", "bearer")
				.build();

		try (PrintWriter pw = resp.getWriter()) {
			pw.println(token.toString());
		}
	}

	private String generateToken(String subject, String issuer) {

		final Date issueDate = new Date();

		String token = Jwts.builder().setSubject(subject)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.setIssuer(issuer)
				.setIssuedAt(issueDate)
				.setExpiration(new Date(issueDate.getTime() + ONE_HOUR))
				.claim("authenticated", true)
				.claim("level", 3)
				.compact();

		return (token);
	}

	
	
}
