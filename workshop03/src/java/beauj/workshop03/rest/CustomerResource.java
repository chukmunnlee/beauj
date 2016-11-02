package beauj.workshop03.rest;

import beauj.workshop03.business.CustomerBean;
import beauj.workshop03.model.Customer;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.persistence.EntityExistsException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

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

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response post(MultivaluedMap<String, String> formData) {

		Customer customer = new Customer();
		customer.setCustomerId(Integer.parseInt(formData.getFirst("custId")));
		customer.setName(formData.getFirst("name"));
		customer.setAddressline1(formData.getFirst("addr1"));
		customer.setAddressline2(formData.getFirst("addr2"));
		customer.setCity(formData.getFirst("city"));
		customer.setZip(formData.getFirst("zip"));
		customer.setState(formData.getFirst("state"));
		customer.setPhone(formData.getFirst("phone"));
		customer.setFax(formData.getFirst("fax"));
		customer.setEmail(formData.getFirst("email"));
		customer.setDiscountCode(formData.getFirst("discount"));
		customer.setCreditLimt(Integer.parseInt(formData.getFirst("credit")));

		try {
			customerBean.add(customer);
		} catch (EntityExistsException ex) {
			return (Response.status(Response.Status.BAD_GATEWAY)
					.entity(ex.getMessage()).build());
		} catch (Throwable t) {
			return (Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(t.getMessage()).build());
		}

		return (Response.status(Response.Status.CREATED).build());
	}
}
