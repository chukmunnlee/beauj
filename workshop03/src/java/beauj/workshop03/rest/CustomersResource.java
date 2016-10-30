package beauj.workshop03.rest;

import beauj.workshop03.business.CustomerBean;
import beauj.workshop03.model.Customer;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("/customers")
public class CustomersResource {

	@EJB private CustomerBean customerBean;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@Context UriInfo ui) {

		UriBuilder uiBuilder = ui.getBaseUriBuilder()
				.path(CustomerResource.class);

		try {
			uiBuilder = uiBuilder.path(CustomerResource.class.getMethod(
					"get", Integer.class));
		} catch (Exception ex) {
			return (Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(ex.getMessage()).build());
		}

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		List<Customer> customers = customerBean.findAll();
		for (Customer c: customers) 
			arrBuilder.add(uiBuilder.clone().build(c.getCustomerId()).toString());

		return (Response.ok(arrBuilder.build()).build());
	}
	
}
