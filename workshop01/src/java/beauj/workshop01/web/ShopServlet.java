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

@WebServlet("/shop")
public class ShopServlet extends HttpServlet {

	@Inject private Cart cart;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String itemName = req.getParameter("name");
		Integer quantity = Integer.parseInt(req.getParameter("quantity"));

		Item item = new Item();
		item.setName(itemName);
		item.setQuantity(quantity);

		cart.addItem(item);

		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		resp.setContentType("text/html");

		try (PrintWriter pw = resp.getWriter()) {
			pw.print("<h1>My Cart</h1>");
			pw.print("<ul>");
			for (Item i: cart.getItems())
				pw.print(String.format("<li>%s (%d)</li>", i.getName(), i.getQuantity()));
			pw.print("</ul>");
			pw.flush();
		}
	}
}
