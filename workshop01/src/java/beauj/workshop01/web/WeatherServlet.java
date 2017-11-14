package beauj.workshop01.web;

import beauj.workshop01.model.WeatherAPI;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@WebServlet(urlPatterns = "/weather")
public class WeatherServlet extends HttpServlet {

	private static final String API_KEY = "83c2bf0010fc49a5a9759839cce6b63f";

	@Inject private WeatherAPI weather;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		final String cityName = req.getParameter("cityName");

		Optional<JsonObject> resultHolder = weather.getWeather(cityName, API_KEY);

		if (resultHolder.isPresent()) {
			resp.setStatus(200);
			resp.setContentType(MediaType.APPLICATION_JSON);
			try (PrintWriter pw = resp.getWriter()) {
				pw.println(resultHolder.get().toString());
			}
			return;
		}

		resp.setStatus(404);
	}
}
