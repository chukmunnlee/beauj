package beauj.workshop01.model;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class WeatherAPI {

	private Client client;

	@PostConstruct
	private void init() {
		client = ClientBuilder.newClient();
	}

	public Optional<JsonObject> getWeather(String city, String apiKey) {

		WebTarget target = client.target("http://api.openweathermap.org/data/2.5/weather")
				.queryParam("q", city)
				.queryParam("appid", apiKey);

		Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON);

		return (Optional.ofNullable(builder.get(JsonObject.class)));
	}

	@PreDestroy
	private void cleanup() {
		client.close();
	}
	
}
