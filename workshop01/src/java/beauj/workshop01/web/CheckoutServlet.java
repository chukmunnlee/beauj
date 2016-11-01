package beauj.workshop01.web;

import beauj.workshop01.model.Cart;
import beauj.workshop01.model.Item;
import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

	@Inject private Cart cart;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		session.invalidate();

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("text/html");

		try (PrintWriter pw = resp.getWriter()) {
			pw.println("<h2>Checkout</h2>");
			pw.println("<p>The contents of your cart:</p>");
			pw.print("<ul>");
			for (Item i: cart.getItems())
				pw.print(String.format("<li>%s (%d)</li>", i.getName(), i.getQuantity()));
			pw.print("</ul>");
			pw.println("<p>Thank you for shopping with us</p>");
			pw.flush();
		}
	}

	
	
}
