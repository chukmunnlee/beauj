package beauj.workshop02.web;

import beauj.workshop02.model.Customer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/customer")
public class CustomerEnquiryServlet extends HttpServlet {

	@Inject private CustomerResults results;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		Integer custId = Integer.parseInt(req.getParameter("custId"));
		String reload = req.getParameter("reload");

		if (Objects.nonNull(reload))
			results.clearEntry(custId);

		Customer customer = results.findByCustomerId(custId);
		if (null == customer) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/plain");
		try (PrintWriter pw = resp.getWriter()) {
			pw.println("Customer name: " + customer.getName());
		}
	}

}
