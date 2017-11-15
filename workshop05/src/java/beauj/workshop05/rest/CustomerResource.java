package beauj.workshop05.rest;

import beauj.workshop05.business.CustomerBean;
import beauj.workshop05.model.Customer;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@EJB private CustomerBean customerBean;

	@GET
	@Path("{cid}")
	public Response get(@PathParam("cid") Integer customerId) {

		Optional<Customer> opt = customerBean.findById(customerId);

		if (!opt.isPresent())
			return (Response.status(Response.Status.NOT_FOUND)
					.entity(Json.createObjectBuilder()
							.add("error", String.format("Customer %d not found", customerId))
							.build())
					.build());
		return (Response.ok(opt.get().toJSON()).build());
	}
}
